package com.kon.framework.netty.server;

import com.kon.framework.core.factory.ExecutorFactory;
import com.kon.framework.netty.config.KonNettyProperties;
import com.kon.framework.netty.initializer.ServerChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Netty客户端
 *
 * @author Kong, created on 2020-12-23T15:33.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
@Component
public class NettyClient {

    @Autowired
    private KonNettyProperties konNettyProperties;

    /**
     * loop事件组[TCP客户端]
     */
    private EventLoopGroup loopGroup;
    /**
     * Bootstrap[TCP客户端]
     */
    private Bootstrap bootstrap;

    /**
     * 通道
     */
    private Channel channel;

    /**
     * 初始化客户端服务
     */
    public void initClient() {
        log.info("init kon netty client...");

        this.loopGroup = new NioEventLoopGroup();

        this.bootstrap = new Bootstrap()
                .group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ServerChannelInitializer(this.konNettyProperties.getIdle()));

        this.bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.konNettyProperties.getOption().getConnectTimeOutMs())
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(this.konNettyProperties.getOption().getRcvBufSize()));
    }

    public void startup() {
        String host = this.konNettyProperties.getHost();
        log.info("start kon netty client..., this address is ({}:{})", host, this.konNettyProperties.getPort());
        if (StringUtils.isEmpty(host)) {
            throw new NullPointerException("host is null");
        }
        ExecutorFactory.getExecutorService(getClass()).execute(this::connect);
    }

    /**
     * 启动Netty服务
     */
    private void connect() {
        // 服务器创建绑定
        ChannelFuture channelFuture = this.bootstrap.connect(this.konNettyProperties.getHost(), this.konNettyProperties.getPort());
        // 监听器
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                //注册连接事件
                log.info("The client [{}] connected...", channelFuture.channel().localAddress().toString());
                this.channel = channelFuture.channel();
            } else {
                log.error("create client error!!!");
            }
        });
        channelFuture.channel().closeFuture().addListener(cf -> close());
    }

    /**
     * 客户端关闭
     */
    private void close() {
        //关闭客户端套接字
        if (this.channel != null) {
            this.channel.close();
        }
        //关闭客户端线程组
        this.loopGroup.shutdownGracefully();
    }

    /**
     * 关闭
     */
    public void stop() {
        try {
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 发送指令
     * @param message    Message
     * @throws InterruptedException 中断异常
     */
    public void send(Object message) throws InterruptedException {
        if (null != this.channel && this.channel.isActive()) {
            ChannelFuture channelFuture =  this.channel.writeAndFlush(message).sync();
            if (channelFuture.isSuccess()) {
                log.error("writeAndFlush msg Success！！！: {}", message);
            } else {
                log.error("writeAndFlush msg error: {}", message);
            }
        } else {
            retryConnect();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 重新连接
     */
    private void retryConnect() {

        this.connect();
    }
}
