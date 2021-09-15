package com.kon.framework.netty.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

/**
 * 默认服务通道通知服务
 *
 * @author Kong, created on 2020-12-23T16:16.
 * @version 1.0.0-SNAPSHOT
 */
@Log4j2
public class DefaultServerChannelNotify implements ServerChannelNotify {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        log.info("channel({}) register!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        log.info("channel({}) unregister!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channel({}) active!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("channel({}) inactive!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("channel({}) read msg ({})", ctx.channel().id().asLongText(), msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("channel({}) read complete!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        log.info("channel({}) event triggered!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void channelWriteAbilityChanged(ChannelHandlerContext ctx) {
        log.info("channel({}) write ability changed!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("channel({}) exception({})", ctx.channel().id().asLongText(), ExceptionUtil.getMessage(cause));
    }

    @Override
    public void readIdleTimeOut(ChannelHandlerContext ctx) {
        log.info("channel({}) read idle timeOut!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void writeIdleTimeOut(ChannelHandlerContext ctx) {
        log.info("channel({}) write idle timeOut!!!", ctx.channel().id().asLongText());
    }

    @Override
    public void allIdleTimeOut(ChannelHandlerContext ctx) {
        log.info("channel({}) all idle timeOut!!!", ctx.channel().id().asLongText());
    }
}
