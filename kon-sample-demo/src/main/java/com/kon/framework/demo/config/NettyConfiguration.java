package com.kon.framework.demo.config;

import com.kon.framework.netty.config.KonNettyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Netty 配置
 *
 * @author Kong, created on 2020-12-24T14:41.
 * @version 1.0.0-SNAPSHOT
 */
@Configuration
public class NettyConfiguration {

    @Bean
    public KonNettyConfiguration konNettyConfiguration() {
        return new KonNettyConfiguration();
    }
}
