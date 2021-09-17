package com.kon.framework.mybatis.annotation;

import com.kon.framework.mybatis.core.Order;

import java.lang.annotation.*;

/**
 * 排序注解
 *
 * @author Kong, created on 2020-12-18T16:24.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KonOrder {

    Order order() default Order.DESC;
}
