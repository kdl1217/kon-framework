package com.kon.framework.netty.config.data;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * IdleState Properties
 *
 * @author Kong, created on 2020-12-23T12:11.
 * @version 1.0.0-SNAPSHOT
 */
@Data
public class IdleState {

    /**
     * 默认闲置读时间
     */
    private static long DEFAULT_READER_IDLE_TIME = 0L;
    /**
     * 默认闲置写时间
     */
    private static long DEFAULT_WRITER_IDLE_TIME = 0L;
    /**
     * 默认全部闲置时间
     */
    private static long DEFAULT_ALL_IDLE_TIME = 60L;
    /**
     * 默认时间单位
     */
    private static String DEFAULT_TIME_UNIT = "seconds";

    /**
     * 闲置读时间
     */
    private long readerIdleTime = DEFAULT_READER_IDLE_TIME;
    /**
     * 闲置写时间
     */
    private long writerIdleTime = DEFAULT_WRITER_IDLE_TIME;
    /**
     * 全部闲置时间
     */
    private long allIdleTime = DEFAULT_ALL_IDLE_TIME;
    /**
     * 时间单位
     */
    private String timeUnit = DEFAULT_TIME_UNIT;

    /**
     * 获取闲置时间单位
     * @return TimeUnit
     */
    public TimeUnit getIdleTimeUnit() {
        return TimeUnit.valueOf(timeUnit.toUpperCase());
    }
}
