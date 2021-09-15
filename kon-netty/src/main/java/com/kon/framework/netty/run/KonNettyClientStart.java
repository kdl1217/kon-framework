package com.kon.framework.netty.run;

import com.kon.framework.netty.server.NettyClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * netty 服务启动
 *
 * @author Kong, created on 2020-12-24T14:20.
 * @version 1.0.0-SNAPSHOT
 */
@Log4j2
@Configuration
@ConditionalOnProperty(prefix = "kon.netty", name = "terminal", havingValue = "client")
public class KonNettyClientStart {

    @Autowired
    private NettyClient nettyClient;

    @Bean
    public void startClient() {
        this.nettyClient.initClient();
        this.nettyClient.startup();
    }
}
