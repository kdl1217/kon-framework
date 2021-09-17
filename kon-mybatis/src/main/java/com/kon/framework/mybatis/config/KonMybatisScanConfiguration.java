package com.kon.framework.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.kon.framework.mybatis.plugin.PaginationInterceptor;
import com.kon.framework.mybatis.properties.MybatisProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Mybatis 扫描包
 *
 * @author Kong, created on 2020-12-25T13:29.
 * @version 1.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan("com.kon.framework.mybatis")
public class KonMybatisScanConfiguration {

    @Autowired
    private MybatisProperties mybatisProperties;

    /**
     * mybatis 自定义拦截器
     */
    @Bean
    public Interceptor getInterceptor(){
        // 添加分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        if (null != this.mybatisProperties && StrUtil.isNotBlank(this.mybatisProperties.getDialect())) {
            Properties properties = new Properties();
            properties.setProperty("dialect", this.mybatisProperties.getDialect());
            paginationInterceptor.setProperties(properties);
        }
        return paginationInterceptor;
    }

}
