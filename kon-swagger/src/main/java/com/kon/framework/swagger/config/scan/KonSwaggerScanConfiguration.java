package com.kon.framework.swagger.config.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * KonSwagger 扫描包
 *
 * @author Kong, created on 2020-05-14T11:14.
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("com.kon.framework.swagger")
public class KonSwaggerScanConfiguration {

}
