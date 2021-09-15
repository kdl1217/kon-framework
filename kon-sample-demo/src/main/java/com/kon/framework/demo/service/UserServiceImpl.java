package com.kon.framework.demo.service;

import com.kon.framework.core.share.Pagination;
import com.kon.framework.demo.bean.UserBean;
import com.kon.framework.demo.dao.UserDao;
import com.kon.framework.mybatis.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 用户接口实现
 *
 * @author Kong, created on 2020-12-15T15:49.
 * @version 1.0.0-SNAPSHOT
 */
@Service
public class UserServiceImpl extends BaseRepository<UserBean, Long> implements UserService  {

    @Autowired
    private UserDao userDao;

    @Override
    public UserBean findId(long id) {
        return super.findId(id);
    }

    @Override
    public UserBean findByName(String name) {
        return super.findOne(Collections.singletonMap("name", name));
    }

    @Override
    public int save(String name, Integer age) {
        UserBean userBean = new UserBean();
        userBean.setName(name);
        userBean.setAge(age);
        return super.save(userBean);
    }

    @Override
    public int update(Long id, String name) {
        UserBean userBean = new UserBean();
        userBean.setId(id);
        userBean.setName(name);
        return super.update(userBean);
    }

    @Override
    public Pagination<UserBean> findPageList(Integer pageNum, Integer pageSize) {
        return super.findPageList(pageNum, pageSize);
    }

    @Override
    public List<UserBean> findAll() {
//        Map<String, Object> map = new HashMap<>();
        return userDao.findAllUser();
    }


}
