package com.kon.framework.mybatis.base;

import com.kon.framework.core.init.SpringInitialize;
import com.kon.framework.core.share.Pagination;
import com.kon.framework.mybatis.handler.BaseMapperHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 基础Repository
 *
 * @author Kong, created on 2020-12-16T17:52.
 * @version 1.0.0-SNAPSHOT
 */
@Log4j2
public class BaseRepository<T, S extends Serializable> extends BaseMapperHandler<T> implements SpringInitialize {

    @Override
    public void init(ContextRefreshedEvent event) {
        // 初始化注入SQL
        super.initHandler();
    }

    /**
     * 通过ID查询对象
     * @param id    ID
     * @return  对象
     */
    public T findId(S id) {
        return this.sqlSession.selectOne(super.getMappedStatementId(super.FIND_BY_ID_MAPPER), id);
    }

    /**
     * 根据对象属性查询对象
     * @param model 对象不能为空
     * @return 对象
     */
    public T findOne(T model) {
        if (ObjectUtils.isEmpty(model)) {
            throw new NullPointerException("model param is null, please input param!!!");
        }
        return this.sqlSession.selectOne(super.getMappedStatementId(super.FIND_ONE_MAPPER), model);
    }

    /**
     * 查询单条记录
     * @param params    Map集合 key -> Bean Filed , value -> param
     * @return  对象
     */
    public T findOne(Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params)) {
            throw new NullPointerException("map params is null, please input param!!!");
        }
        return this.sqlSession.selectOne(super.getMappedStatementId(super.FIND_ONE_MAPPER), params);
    }

    /**
     * 保存对象信息
     * @param model 对象
     * @return  操作数量
     */
    public int save(T model) {
        if (ObjectUtils.isEmpty(model)) {
            throw new NullPointerException("model params is null, please input param!!!");
        }
        return sqlSession.insert(super.getMappedStatementId(super.SAVE_MAPPER), model);
    }

    /**
     * 修改对象
     * @param model 对象
     * @return 操作数量
     */
    public int update(T model) {
        if (ObjectUtils.isEmpty(model)) {
            throw new NullPointerException("model params is null, please input param!!!");
        }
        return sqlSession.update(super.getMappedStatementId(super.UPDATE_MAPPER), model);
    }

    /**
     * 根据ID删除对象
     * @param id ID
     * @return 操作数量
     */
    public int deleteById(S id) {
        return sqlSession.delete(super.getMappedStatementId(super.DELETE_MAPPER), id);
    }

    /**
     * 查询全部集合
     * @return 对象集合
     */
    public List<T> findList() {
        return findList(this.initModel);
    }

    /**
     * 根据Map集合参数查询集合
     * @param params 集合参数
     * @return 对象集合
     */
    public List<T> findList(Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params)) {
            params = Collections.emptyMap();
        }
        return sqlSession.selectList(super.getMappedStatementId(super.FIND_LIST_MAPPER), params);
    }

    /**
     * 根据对象属性查询集合
     * @param model 对象
     * @return 对象集合
     */
    public List<T> findList(T model) {
        if (null == model) {
            model = super.initModel;
        }
        return sqlSession.selectList(super.getMappedStatementId(super.FIND_LIST_MAPPER), model);
    }

    /**
     * 分页查询列表
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @return  分页对象
     */
    public Pagination<T> findPageList(Integer pageNum, Integer pageSize) {
        return findPageList(pageNum, pageSize, this.initModel);
    }

    /**
     * 根据对象参数分页查询列表
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @return  分页对象
     */
    public Pagination<T> findPageList(Integer pageNum, Integer pageSize, T model) {
        if (null == model) {
            model = super.initModel;
        }
        Pagination<T> pagination = new Pagination<>(pageNum, pageSize);
        Long total = sqlSession.selectOne(super.getMappedStatementId(super.FIND_COUNT_MAPPER), model);
        pagination.setTotalData(total);
        if (0 == total) {
            pagination.setTotalPage(0L);
            return pagination;
        }
        RowBounds rowBounds = new RowBounds((pageNum - 1) * pageSize, pageSize);
        long totalPage = (total - 1) / pageSize + 1;
        pagination.setTotalPage(totalPage);
        pagination.setList(sqlSession.selectList(super.getMappedStatementId(super.FIND_LIST_MAPPER), model, rowBounds));
        return pagination;
    }


    /**
     * 根据Map集合参数分页查询列表
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @return  分页对象
     */
    public Pagination<T> findPageList(Integer pageNum, Integer pageSize, Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params)) {
            params = Collections.emptyMap();
        }
        Pagination<T> pagination = new Pagination<>(pageNum, pageSize);
        Long total = sqlSession.selectOne(super.getMappedStatementId(super.FIND_COUNT_MAPPER), params);
        pagination.setTotalData(total);
        if (0 == total) {
            pagination.setTotalPage(0L);
            return pagination;
        }
        RowBounds rowBounds = new RowBounds((pageNum - 1) * pageSize, pageSize);
        long totalPage = (total - 1) / pageSize + 1;
        pagination.setTotalPage(totalPage);
        pagination.setList(sqlSession.selectList(super.getMappedStatementId(super.FIND_LIST_MAPPER), params, rowBounds));
        return pagination;
    }
}