package com.kon.framework.mybatis.annotation;

import com.kon.framework.core.annotation.EnableSpringAware;
import com.kon.framework.core.annotation.EnableSpringInitializeListener;
import com.kon.framework.mybatis.config.KonMybatisScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动KonMybatis
 *
 * @author Kong, created on 2020-12-17T11:26.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableSpringAware
@EnableSpringInitializeListener
@Import(KonMybatisScanConfiguration.class)
@Documented
public @interface EnableKonMybatis {

}
