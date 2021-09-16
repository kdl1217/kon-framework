package com.kon.framework.mybatis.plugin;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 解决分页查询问题
 *
 * @author Kong, created on 2020-12-15T11:59.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class PaginationInterceptor implements Interceptor {

    private static final String MYSQL = "mysql";

    private static final String ORACLE = "oracle";

    private static final String DB2 = "db2";

    private String dialect = "";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (StrUtil.isEmpty(dialect)) {
            throw new Exception("null dialect");
        }

        // 获取被拦截方法的所有参数
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object queryParameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        // 获取SQL语句
        String sqlId = ms.getId();
        BoundSql boundSql = ms.getBoundSql(queryParameter);
        String querySql = boundSql.getSql();
        if (StrUtil.isEmpty(querySql)) {
            log.info("the sql whose id is {} is blank!", sqlId);
            throw new NullPointerException("the sql whose id is  " + sqlId + " is blank!");
        }

        if (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT) {
            return invocation.proceed();
        }
        // 拼SQL
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, splicingSql(querySql, offset, limit));

        MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSource(newBoundSql));
        // 覆盖第一个参数
        invocation.getArgs()[0] = newMs;
        // 重置offset
        invocation.getArgs()[2] = new RowBounds(0, limit);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 从配置文件获取数据库方言名称
        dialect = properties.getProperty("dialect");
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        // 坑爹啊，原来版本的keyProperty参数是String[],到这个版本就成了String了，害得我自己拼字符串
        StringBuilder propertiesStr = new StringBuilder();
        if (null != ms.getKeyProperties() && 0 != ms.getKeyProperties().length) {
            for (String prop : ms.getKeyProperties()) {
                propertiesStr.append(",").append(prop);
            }
            propertiesStr.substring(1);
        }

        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        return builder.resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .keyProperty(propertiesStr.toString())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultMaps(ms.getResultMaps())
                .cache(ms.getCache())
                .build();
    }

    /**
     * BoundSqlSource
     */
    public static class BoundSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());

        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * 拼接SQL分页
     * @param querySql  查询SQL语句
     * @param offset    页数
     * @param limit     限制条数
     * @return  拼接SQL
     */
    private String splicingSql(String querySql, int offset, int limit) {
        if (ORACLE.equals(dialect.toLowerCase())) {
            int maxRn = offset + limit;
            querySql = "select * from (select tp9999999999999.*,rownum rn from (" + querySql
                    + ") tp9999999999999 where rownum <= " + maxRn + ") where rn>=" + offset;
        }
        if (MYSQL.equals(dialect.toLowerCase())) {
            querySql = querySql + "  limit  " + offset + "," + limit;
        }
        if (DB2.equals(dialect.toLowerCase())) {
            int endNumber = offset + limit;
            querySql = "select * from (select B9999999.* ,rownumber() over() as rn  from (" + querySql
                    + ") as B9999999) as A9999999 where A9999999.rn between " + offset + " and " + endNumber;
        }
        return querySql;
    }

}
