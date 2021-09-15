package com.kon.framework.demo.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
 *
 * @author Kong, created on 2020-05-13T13:58.
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
public class MyBatisConfiguration {

    /*@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.kon.framework.dao");

        return mapperScannerConfigurer;
    }*/

}
