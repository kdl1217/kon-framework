package com.kon.framework.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 不参与查询列
 *
 * @author Kong, created on 2020-12-17T10:27.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisableColumn {
}
