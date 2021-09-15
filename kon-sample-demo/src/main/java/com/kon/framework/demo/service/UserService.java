package com.kon.framework.demo.service;

import com.kon.framework.demo.bean.UserBean;
import com.kon.framework.core.share.Pagination;

import java.util.List;

/**
 * 用户接口
 *
 * @author Kong, created on 2020-12-15T15:47.
 * @version 1.0.0-SNAPSHOT
 */
public interface UserService {

    /**
     * 根据ID查询对象
     * @param id    主键ID
     * @return 用户信息
     */
    UserBean findId(long id);

    /**
     * 根据用户名查询对象
     * @param name    用户名
     * @return 用户信息
     */
    UserBean findByName(String name);

    /**
     * 保存用户信息
     * @param name 用户名
     * @param age  年龄
     * @return  操作条数
     */
    int save(String name, Integer age);

    /**
     * 修改用户信息
     * @param id    主键ID
     * @param name 用户名
     * @return  操作条数
     */
    int update(Long id, String name);

    /**
     * 分页查询用户信息
     * @param pageNum   当前页数
     * @param pageSize  每页条数
     * @return  分页用户信息
     */
    Pagination<UserBean> findPageList(Integer pageNum, Integer pageSize);

    /**
     * 查询所有用户信息
     * @return 用户集合
     */
    List<UserBean> findAll();
}
