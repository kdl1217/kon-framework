package com.kon.framework.mybatis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 表名
 *  不添加注解，默认的表名以大写转下划线的形式
 * @author Kong, created on 2020-12-17T09:45.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KonTable {

    String name() default "";

    @AliasFor("name")
    String value() default "";
}
