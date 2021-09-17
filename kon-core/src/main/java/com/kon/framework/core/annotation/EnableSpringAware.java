package com.kon.framework.core.annotation;

import com.kon.framework.core.init.config.SpringAwareConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Spring Aware
 *  注入 SpringUtil
 *
 * @author Kong, created on 2020-12-15T11:59.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringAwareConfiguration.class})
@Documented
public @interface EnableSpringAware {
}
