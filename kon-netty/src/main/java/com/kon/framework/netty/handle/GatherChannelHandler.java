package com.kon.framework.netty.handle;

import com.kon.framework.netty.service.ServerChannelNotify;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.log4j.Log4j2;

/**
 * 采集通道处理器
 *
 * @author Kong, created on 2020-12-23T14:04.
 * @version 1.0.0-SNAPSHOT
 */
@Log4j2
@ChannelHandler.Sharable
public class GatherChannelHandler extends ChannelInboundHandlerAdapter {

    private final ServerChannelNotify channelNotify;

    public GatherChannelHandler(ServerChannelNotify channelNotify) {
        this.channelNotify = channelNotify;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.channelNotify.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                this.channelNotify.readIdleTimeOut(ctx);
            } else if (event.state() == IdleState.WRITER_IDLE) {
                this.channelNotify.writeIdleTimeOut(ctx);
            } else if (event.state() == IdleState.ALL_IDLE) {
                this.channelNotify.allIdleTimeOut(ctx);
            }
        }
        this.channelNotify.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        this.channelNotify.channelWriteAbilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.channelNotify.exceptionCaught(ctx, cause);
    }
}
