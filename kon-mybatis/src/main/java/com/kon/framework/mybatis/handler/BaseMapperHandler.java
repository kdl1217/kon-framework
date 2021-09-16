package com.kon.framework.mybatis.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.kon.framework.mybatis.annotation.*;
import com.kon.framework.mybatis.core.Order;
import com.kon.framework.mybatis.properties.MybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 基础MapperHandler
 *
 * @author Kong, created on 2020-12-18T10:07.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
public class BaseMapperHandler<T> extends XmlMapperHandler<T>{

    /**
     * 根据主键查询对象MapperId
     */
    protected final String FIND_BY_ID_MAPPER = "kon-findById";
    /**
     * 根据查询一个对象MapperId
     */
    protected final String FIND_ONE_MAPPER = "kon-findOne";
    /**
     * 保存对象MapperId
     */
    protected final String SAVE_MAPPER = "kon-save";
    /**
     * 修改对象MapperId
     */
    protected final String UPDATE_MAPPER = "kon-update";
    /**
     * 删除对象MapperId
     */
    protected final String DELETE_MAPPER = "kon-delete";
    /**
     * 查询总条数MapperId
     */
    protected final String FIND_COUNT_MAPPER = "kon-findCount";
    /**
     * 查询集合MapperId
     */
    protected final String FIND_LIST_MAPPER = "kon-findList";

    protected SqlSession sqlSession;

    protected T initModel;

    /**
     * 初始化Handler
     */
    protected void initHandler() {
        // 加载Class
        this.loadingClazz();
        // 加载表名
        this.loadingTableName();
        // 加载Column
        this.loadingColumns();
        // 引入SqlSession
        this.sqlSession = SpringUtil.getBean(SqlSession.class);
        // 设置自动生成Key
        this.sqlSession.getConfiguration().setUseGeneratedKeys(true);
        // 添加基础Mapper
        this.addAllMapper();
    }

    /**
     * 加载Class
     */
    @SuppressWarnings("unchecked")
    private void loadingClazz() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
        if (null == this.clazz) {
            throw new IllegalArgumentException("this class loading failure!!!");
        }
        // 加载初始化对象
        loadingInitModel();
    }

    /**
     * 加载初始化对象
     */
    private void loadingInitModel() {
        try {
            this.initModel = this.clazz.newInstance();
            Field[] fields = this.initModel.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(this.initModel, null);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载表名
     */
    private void loadingTableName() {
        KonTable konTable = this.clazz.getAnnotation(KonTable.class);
        if (null != konTable) {
            this.tableName = MybatisProperties.KON_MYBATIS_TABLE_PREFIX + konTable.value();
        } else {
            this.tableName = MybatisProperties.KON_MYBATIS_TABLE_PREFIX + humpToLine(this.clazz.getSimpleName());
        }
    }

    /**
     * 加载Column
     */
    private void loadingColumns() {
        Field[] fields = this.clazz.getDeclaredFields();
        this.columns = new HashMap<>(fields.length);
        for (Field field : fields) {
            DisableColumn disableColumn = field.getAnnotation(DisableColumn.class);
            if (null == disableColumn) {
                // 设置Column
                setColumns(field);
                // 设置主键
                setPrimaryKey(field);
                // 设置排序
                setOrder(field);
            }
        }
    }

    /**
     * 设置Column
     * @param field 属性值
     */
    private void setColumns(Field field) {
        KonColumn konColumn = field.getAnnotation(KonColumn.class);
        if (null != konColumn) {
            String column = konColumn.value();
            column = StrUtil.isEmpty(column) ? humpToLine(field.getName()) :  column;
            this.columns.put(field.getName(), column);
        } else {
            this.columns.put(field.getName(), humpToLine(field.getName()));
        }
    }

    /**
     * 设置主键
     * @param field 属性值
     */
    private void setPrimaryKey(Field field) {
        KonPrimaryKey konPrimaryKey = field.getAnnotation(KonPrimaryKey.class);
        if (null != konPrimaryKey) {
            super.primaryKey = konPrimaryKey.value();
        }
    }

    /**
     * 设置排序
     * @param field 属性值
     */
    private void setOrder(Field field) {
        KonOrder konOrder = field.getAnnotation(KonOrder.class);
        if (null != konOrder) {
            if (konOrder.order() == Order.ASC) {
                super.ascColumn = this.columns.get(field.getName());
            } else if (konOrder.order() == Order.DESC) {
                super.descColumn = this.columns.get(field.getName());
            }
        }
    }

    /**
     * 大写转下划线 eg: tableName -> table_name
     * @param str   转换字符
     * @return  带下划线字符
     */
    private String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 添加基础的Mapper
     */
    private void addAllMapper() {
        // 添加insertMapper
        this.addSaveMapper();
        // 添加updateMapper
        this.addUpdateMapper();
        // 添加deleteMapper
        this.addDeleteMapper();
        // 添加findByIdMapper
        this.addFindByIdMapper();
        // 添加findOneMapper
        this.addFindOneMapper();
        // 添加findCountMapper
        this.addFindCountMapper();
        // 添加findListMapper
        this.addFindListMapper();
    }

    protected void addSaveMapper() {
        String statementId = getMappedStatementId(SAVE_MAPPER);
        addMapper(super.saveXmlMapper(), statementId, SqlCommandType.INSERT, Integer.class);
    }

    protected void addUpdateMapper() {
        String statementId = getMappedStatementId(UPDATE_MAPPER);
        addMapper(super.updateXmlMapper(), statementId, SqlCommandType.UPDATE, Integer.class);
    }

    protected void addDeleteMapper() {
        String statementId = getMappedStatementId(DELETE_MAPPER);
        addMapper(super.deleteXmlMapper(), statementId, SqlCommandType.DELETE, Integer.class);
    }

    protected void addFindByIdMapper() {
        String statementId = getMappedStatementId(FIND_BY_ID_MAPPER);
        addMapper(super.selectByIdMapper(), statementId, SqlCommandType.SELECT, this.clazz);
    }

    protected void addFindOneMapper() {
        String statementId = getMappedStatementId(FIND_ONE_MAPPER);
        addMapper(super.selectOneMapper(), statementId, SqlCommandType.SELECT, this.clazz);
    }

    protected void addFindCountMapper() {
        String statementId = getMappedStatementId(FIND_COUNT_MAPPER);
        addMapper(super.selectCountMapper(), statementId, SqlCommandType.SELECT, Long.class);
    }

    protected void addFindListMapper() {
        String statementId = getMappedStatementId(FIND_LIST_MAPPER);
        addMapper(super.selectList(), statementId, SqlCommandType.SELECT, this.clazz);
    }

    /**
     * 获取MappedStatementId
     * @param mapperId  mapperId
     * @return  StatementId
     */
    protected String getMappedStatementId(String mapperId) {
        return this.clazz.getCanonicalName() + "." + mapperId;
    }

    /**
     * 添加XMLMapper到MybatisMapper里面
     * @param xmlMapper         XMLMapper
     * @param statementId       StatementId
     * @param commandType       命令类型 {@link SqlCommandType}
     * @param returnClazz       返回类型
     */
    private void addMapper(String xmlMapper, String statementId, SqlCommandType commandType, Class<?> returnClazz) {
        // 1. 对executeSql 加上script标签
        Configuration configuration = sqlSession.getConfiguration();
        // 2. languageDriver 是帮助我们实现dynamicSQL的关键
        LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
        //  泛型化入参
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, "<script>" + xmlMapper + "</script>", this.clazz);
        newMappedStatement(configuration, statementId, sqlSource, commandType, returnClazz);
    }

    /**
     * 添加新的MappedStatement
     * @param configuration Mybatis Configuration
     * @param statementId   MappedStatementId
     * @param sqlSource     SQLSource
     * @param commandType   命令类型 {@link SqlCommandType}
     * @param returnClazz   返回类型
     */
    private void newMappedStatement(Configuration configuration, String statementId, SqlSource sqlSource,
                                    SqlCommandType commandType, Class<?> returnClazz) {
        if (!checkMappedStatement(configuration, statementId)) {
            // 构建一个 select 类型的ms ，通过制定SqlCommandType.SELECT 注意！！-》 这里可以指定你想要的任何配置，比如cache,CALLABLE,
            MappedStatement ms = new MappedStatement
                    .Builder(configuration, statementId, sqlSource, commandType)
                    .resultMaps(Collections.singletonList(
                            new ResultMap.Builder(configuration, "defaultResultMap", returnClazz, new ArrayList<>(0)).build()))
                    .build();
            // 加入到此中去
            configuration.addMappedStatement(ms);
        }
    }

    /**
     * 检查 StatementId 是否存在
     * @param configuration Mybatis Configuration
     * @param statementId   StatementId
     * @return 是否存在
     */
    private boolean checkMappedStatement(Configuration configuration, String statementId) {
        try {
            MappedStatement statement = configuration.getMappedStatement(statementId);
            log.warn("configuration statementId has exist：{}", statement.getId());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
