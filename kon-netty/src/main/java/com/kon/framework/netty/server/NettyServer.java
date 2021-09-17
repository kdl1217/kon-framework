package com.kon.framework.netty.server;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.kon.framework.core.factory.ExecutorFactory;
import com.kon.framework.netty.config.KonNettyProperties;
import com.kon.framework.netty.initializer.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Netty 服务
 *
 * @author Kong, created on 2020-12-23T13:42.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
@Component
public class NettyServer {

    @Autowired
    private KonNettyProperties konNettyProperties;

    /**
     * boss事件组[TCP服务]
     */
    private EventLoopGroup bossGroup;
    /**
     * worker事件组[TCP服务]
     */
    private EventLoopGroup workerGroup;
    /**
     * 服务引导[TCP服务]
     */
    private ServerBootstrap serverBootstrap;

    /**
     * 初始化Tcp服务
     */
    public void initTcpServer() {
        log.info("init kon netty server...");

        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverBootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer(this.konNettyProperties.getIdle()));

        this.serverBootstrap.option(ChannelOption.SO_BACKLOG, this.konNettyProperties.getOption().getBackLogCount())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.konNettyProperties.getOption().getConnectTimeOutMs())
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(this.konNettyProperties.getOption().getRcvBufSize()))
                .childOption(ChannelOption.SO_KEEPALIVE, this.konNettyProperties.getOption().isKeepLive())
                .childOption(ChannelOption.TCP_NODELAY, this.konNettyProperties.getOption().isTcpNoDelay())
                .childOption(ChannelOption.RCVBUF_ALLOCATOR,
                        new FixedRecvByteBufAllocator(this.konNettyProperties.getOption().getRcvBufSize()));
    }

    /**
     * 启动Netty服务端
     * @throws InterruptedException 线程中断异常
     */
    public void startup() throws InterruptedException {
        log.info("start kon netty server...,this port is ({})", this.konNettyProperties.getPort());
        ChannelFuture channelFuture = serverBootstrap.bind(this.konNettyProperties.getPort()).sync();
        ExecutorFactory.getExecutorService(getClass()).execute(() -> {
            try {
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("start netty server error!!! {}", ExceptionUtil.getMessage(e));
            }
        });
    }

    /**
     * 关闭Netty服务
     */
    public void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }






}
