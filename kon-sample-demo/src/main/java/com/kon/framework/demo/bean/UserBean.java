package com.kon.framework.demo.bean;

import com.kon.framework.mybatis.annotation.KonTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author Kong, created on 2020-12-15T15:40.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@NoArgsConstructor
@KonTable("user")
public class UserBean implements Serializable {

    private Long id;

    private Integer age;

    private String name;

    private String password;

    private String text;

}
