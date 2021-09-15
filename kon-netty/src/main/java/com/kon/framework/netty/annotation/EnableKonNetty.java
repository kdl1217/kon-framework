package com.kon.framework.netty.annotation;

import com.kon.framework.netty.config.scan.KonNettyScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动KonNetty
 *
 * @author Kong, created on 2020-12-25T13:33.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(KonNettyScanConfiguration.class)
@Documented
public @interface EnableKonNetty {
}
