package com.kon.framework.mybatis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 重定义列名
 *  不添加注解，默认的列表以下划线的形式查询
 *
 * @author Kong, created on 2020-12-17T10:29.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KonColumn {

    String name() default "";

    @AliasFor("name")
    String value() default "";
}
