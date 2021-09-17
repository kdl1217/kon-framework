package com.kon.framework.mybatis.annotation;

import com.kon.framework.mybatis.core.Condition;
import com.kon.framework.mybatis.core.Group;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 条件
 *
 * @author kon, created on 2021/9/17T11:15.
 * @version 1.0.0-SNAPSHOT
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Conditions {

    /**
     * 所属组，默认是全部
     * @return Group
     */
    Group group() default Group.ALL;

    /**
     * 默认条件等于
     * @return Condition
     */
    Condition condition() default Condition.EQUAL;
}
