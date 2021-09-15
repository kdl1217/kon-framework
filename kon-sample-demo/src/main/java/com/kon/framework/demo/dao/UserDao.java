package com.kon.framework.demo.dao;

import com.kon.framework.demo.bean.UserBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Dao
 *
 * @author Kong, created on 2020-12-15T15:43.
 * @version 1.0.0-SNAPSHOT
 */
@Repository
public interface UserDao {

    /**
     * 查询所有用户信息
     * @param rowBounds 分页对象
     * @return 用户信息集合
     */
    List<UserBean> findAll(RowBounds rowBounds);

    /**
     * 查询所有用户信息
     * @return 用户信息
     */
    List<UserBean> findAllUser();

}
