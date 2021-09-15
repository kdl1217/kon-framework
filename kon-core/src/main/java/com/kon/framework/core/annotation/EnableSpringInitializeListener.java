package com.kon.framework.core.annotation;

import com.kon.framework.core.init.config.SpringInitializeListenerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Spring初始化监听配置
 *
 * @author Kong, created on 2020-12-15T11:59.
 * @version 1.0.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringInitializeListenerConfiguration.class})
@Documented
public @interface EnableSpringInitializeListener {
}
