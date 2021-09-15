package com.kon.framework.demo.controller;

import com.kon.framework.demo.bean.UserBean;
import com.kon.framework.core.share.Pagination;
import com.kon.framework.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户api
 *
 * @author Kong, created on 2020-12-15T15:50.
 * @version 1.0.0-SNAPSHOT
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据ID查询对象
     * @param id    主键ID
     * @return 用户信息
     */
    @GetMapping("/findId/{id}")
    public UserBean findId(@PathVariable("id") long id) {
        return userService.findId(id);
    }

    /**
     * 根据用户名查询对象
     * @param name    用户名
     * @return 用户信息
     */
    @GetMapping("/findByName")
    @ApiOperation("根据用户名查询对象")
    public UserBean findByName(@ApiParam(value = "姓名") @RequestParam("name") String name) {
        return userService.findByName(name);
    }

    /**
     * 保存用户信息
     * @param name 用户名
     * @param age  年龄
     * @return  操作条数
     */
    @GetMapping("/save")
    public int save(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return userService.save(name, age);
    }

    /**
     * 修改用户信息
     * @param id    主键ID
     * @param name 用户名
     * @return  操作条数
     */
    @GetMapping("/update")
    public int update(@RequestParam("id") Long id, @RequestParam("name") String name) {
        return userService.update(id, name);
    }

    @GetMapping("/findPageList/{pageNum}/{pageSize}")
    public Pagination<UserBean> findPageList(@PathVariable("pageNum") Integer pageNum,
                                             @PathVariable("pageSize") Integer pageSize) {
        return userService.findPageList(pageNum, pageSize);
    }


    @GetMapping("/findAllUser")
    public List<UserBean> findAllUser() {
        return userService.findAll();
    }
}
