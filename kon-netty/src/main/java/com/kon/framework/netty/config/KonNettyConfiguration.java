package com.kon.framework.netty.config;

import com.kon.framework.netty.service.DefaultServerChannelNotify;
import com.kon.framework.netty.service.ServerChannelNotify;
import com.kon.framework.netty.share.KonNettyDefine;
import io.netty.channel.ChannelHandler;

/**
 * Kon Netty配置
 *
 * @author Kong, created on 2020-12-24T14:13.
 * @version 1.0.0-SNAPSHOT
 */
public class KonNettyConfiguration {

    /**
     * Netty通道通知
     */
    private ServerChannelNotify channelNotify = new DefaultServerChannelNotify();

    /**
     * 通道处理器
     */
    private ChannelHandler[] channelHandler;

    public ServerChannelNotify getChannelNotify() {
        return channelNotify;
    }

    public KonNettyConfiguration setChannelNotify(ServerChannelNotify channelNotify) {
        this.channelNotify = channelNotify;
        KonNettyDefine.channelNotify = channelNotify;
        return this;
    }

    public ChannelHandler[] getChannelHandler() {
        return channelHandler;
    }

    public KonNettyConfiguration setChannelHandler(ChannelHandler... channelHandler) {
        this.channelHandler = channelHandler;
        KonNettyDefine.channelHandler = channelHandler;
        return this;
    }
}
