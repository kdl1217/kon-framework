package com.kon.framework.swagger.annotation;

import com.kon.framework.swagger.config.scan.KonSwaggerScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动KonSwagger
 *
 * @author Kong, created on 2020-12-25T13:33.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(KonSwaggerScanConfiguration.class)
@Documented
public @interface EnableKonSwagger {
}
