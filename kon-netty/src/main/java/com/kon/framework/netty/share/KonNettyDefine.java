package com.kon.framework.netty.share;

import com.kon.framework.netty.service.DefaultServerChannelNotify;
import com.kon.framework.netty.service.ServerChannelNotify;
import io.netty.channel.ChannelHandler;

/**
 * Kon netty 定义
 *
 * @author Kong, created on 2020-12-24T17:40.
 * @version 1.0.0-SNAPSHOT
 */
public class KonNettyDefine {

    /**
     * Netty通道通知
     */
    public static ServerChannelNotify channelNotify = new DefaultServerChannelNotify();

    /**
     * 通道处理器
     */
    public static ChannelHandler[] channelHandler;
}
