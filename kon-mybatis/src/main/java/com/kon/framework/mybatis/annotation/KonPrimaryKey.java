package com.kon.framework.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 主键Id注解
 *
 * <p>如果不使用注解，则默认是id为主键</p>
 *
 * @author Kong, created on 2020-12-18T16:19.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KonPrimaryKey {

    String value() default "id";
}
