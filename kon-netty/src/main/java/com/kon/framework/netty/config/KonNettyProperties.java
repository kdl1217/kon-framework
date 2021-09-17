package com.kon.framework.netty.config;

import com.kon.framework.netty.config.data.IdleState;
import com.kon.framework.netty.config.data.ServerOption;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Netty Properties
 *
 * @author Kong, created on 2020-12-23T11:41.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kon.netty")
public class KonNettyProperties {

    /**
     * TCP服务端
     */
    public static final String TCP_SERVER = "tcp";
    /**
     * TCP客服端
     */
    public static final String TCP_CLIENT = "client";

    /**
     * 默认 TCP服务端
     */
    private static final String DEFAULT_TERMINAL = TCP_SERVER;

    /**
     * 默认 端口
     */
    private static final int DEFAULT_PORT = 1217;

    /**
     * 终端 : tcp 、client
     */
    private String terminal = DEFAULT_TERMINAL;
    /**
     * 域名，只有当TCP客户端时，才有
     */
    private String host;
    /**
     * 服务端口
     */
    private int port = DEFAULT_PORT;
    /**
     * 闲置状态
     */
    private IdleState idle = new IdleState();
    /**
     * 服务操作
     */
    private ServerOption option = new ServerOption();
}
