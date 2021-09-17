package com.kon.framework.netty.initializer;

import com.kon.framework.netty.config.data.IdleState;
import com.kon.framework.netty.handle.GatherChannelHandler;
import com.kon.framework.netty.share.KonNettyDefine;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 服务通道初始化
 *
 * @author Kong, created on 2020-12-23T13:57.
 * @version 1.0.0-SNAPSHOT
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final IdleState idleState;


    public ServerChannelInitializer(IdleState idleState) {
        this.idleState = idleState;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(idleState.getReaderIdleTime(),
                idleState.getWriterIdleTime(), idleState.getAllIdleTime(), idleState.getIdleTimeUnit()));
        if (null != KonNettyDefine.channelHandler && KonNettyDefine.channelHandler.length > 0) {
            for (ChannelHandler channelHandler : KonNettyDefine.channelHandler) {
                ch.pipeline().addLast(channelHandler);
            }
        }
        ch.pipeline().addLast(new GatherChannelHandler(KonNettyDefine.channelNotify));
        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
    }

}
