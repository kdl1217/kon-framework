package com.kon.framework.mybatis.config.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 扫描包
 *
 * @author Kong, created on 2020-12-25T13:29.
 * @version 1.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("com.kon.framework.mybatis")
public class KonMybatisScanConfiguration {
}
