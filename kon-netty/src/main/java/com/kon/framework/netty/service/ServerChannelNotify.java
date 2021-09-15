package com.kon.framework.netty.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * 服务通道通知服务
 *
 * @author Kong, created on 2020-12-23T14:10.
 * @version 1.0.0-SNAPSHOT
 */
public interface ServerChannelNotify {

    /**
     * 通道注册通知
     * @param ctx ChannelHandlerContext
     */
    default void channelRegistered(ChannelHandlerContext ctx) {

    }

    /**
     * 通道注销通知
     * @param ctx ChannelHandlerContext
     */
    default void channelUnregistered(ChannelHandlerContext ctx) {

    }

    /**
     * 通道激活通知
     * @param ctx ChannelHandlerContext
     */
    default void channelActive(ChannelHandlerContext ctx) {

    }

    /**
     * 通道失活通知
     * @param ctx ChannelHandlerContext
     */
    default void channelInactive(ChannelHandlerContext ctx) {

    }

    /**
     * 通道消息通知
     * @param ctx ChannelHandlerContext
     * @param msg 消息
     */
    void channelRead(ChannelHandlerContext ctx, Object msg);

    /**
     * 通道消息读取完成通知
     * @param ctx ChannelHandlerContext
     */
    default void channelReadComplete(ChannelHandlerContext ctx) {

    }

    /**
     * 通道事件触发通知
     * @param ctx ChannelHandlerContext
     * @param evt 事件
     */
    default void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    /**
     * 通道可写状态发生更改通知
     * @param ctx ChannelHandlerContext
     */
    default void channelWriteAbilityChanged(ChannelHandlerContext ctx) {

    }

    /**
     * 通道异常通知
     * @param ctx   ChannelHandlerContext
     * @param cause 异常
     */
    default void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    /**
     * 读闲置超时
     * @param ctx   ChannelHandlerContext
     */
    default void readIdleTimeOut(ChannelHandlerContext ctx) {

    }

    /**
     * 写闲置超时
     * @param ctx   ChannelHandlerContext
     */
    default void writeIdleTimeOut(ChannelHandlerContext ctx) {

    }

    /**
     * 全部闲置超时
     * @param ctx   ChannelHandlerContext
     */
    default void allIdleTimeOut(ChannelHandlerContext ctx) {

    }
}
