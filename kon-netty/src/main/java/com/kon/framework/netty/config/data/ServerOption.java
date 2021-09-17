package com.kon.framework.netty.config.data;

import lombok.Data;

/**
 * 服务操作
 *
 * @author Kong, created on 2020-12-23T14:42.
 * @version 1.0.0-SNAPSHOT
 */
@Data
public class ServerOption {

    private static final int DEFAULT_BACK_LOG_COUNT = 1024 * 200;

    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 30000;

    private static final int DEFAULT_RCV_BUF_SIZE = 1024;

    private static final boolean DEFAULT_KEEP_LIVE = true;

    private static final boolean DEFAULT_TCP_NO_DELAY = true;

    /**
     * 初始化服务端可连接队列
     */
    private int backLogCount = DEFAULT_BACK_LOG_COUNT;
    /**
     * 连接超时时间
     */
    private int connectTimeOutMs = DEFAULT_CONNECT_TIMEOUT_MS;
    /**
     * 缓冲区字节数
     */
    private int rcvBufSize = DEFAULT_RCV_BUF_SIZE;
    /**
     * 通道保持活跃
     */
    private boolean keepLive = DEFAULT_KEEP_LIVE;
    /**
     * Nagle算法，组装成大的数据包进行发送
     */
    private boolean tcpNoDelay = DEFAULT_TCP_NO_DELAY;
}
