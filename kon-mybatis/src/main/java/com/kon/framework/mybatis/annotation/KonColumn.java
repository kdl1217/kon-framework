package com.kon.framework.mybatis.annotation;

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

    /**
     * 列名，默认是 驼峰格式
     * @return 列名
     */
    String value() default "";

    /**
     * 条件
     * @return Conditions
     */
    Conditions[] conditions() default @Conditions;
}
