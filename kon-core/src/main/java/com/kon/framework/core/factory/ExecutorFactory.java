package com.kon.framework.core.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池管理
 *
 * @author Kong, created on 2020-12-15T10:22.
 * @version 1.0.0-SNAPSHOT
 */
public class ExecutorFactory {

    private static final String SUFFIX_POOL = "-pool";

    private static final String DEFAULT_THREAD_NAME = "executor" + SUFFIX_POOL;

    private static final int CORE_POOL_SIZE = 50;

    private static final int MAXIMUM_POOL_SIZE = 200;

    private static final long KEEP_ALIVE_TIME = 0L;

    private static final Map<String, ExecutorService> EXECUTOR_SERVICE_MAP = new ConcurrentHashMap<>();

    private static final Map<String, ScheduledThreadPoolExecutor> SCHEDULED_THREAD_MAP = new ConcurrentHashMap<>();

    public static ExecutorService getExecutorService() {
        return getExecutorService(DEFAULT_THREAD_NAME);
    }

    public static ExecutorService getExecutorService(Class<?> clazz) {
        return getExecutorService(clazz.getSimpleName() + SUFFIX_POOL);
    }

    public static ExecutorService getExecutorService(String threadName) {
        ExecutorService executorService = EXECUTOR_SERVICE_MAP.get(threadName);
        if (null == executorService) {
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadName).build();
            executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
            EXECUTOR_SERVICE_MAP.put(threadName, executorService);
            return executorService;
        }
        return executorService;
    }

    public static ScheduledThreadPoolExecutor getScheduledThreadExecutor() {
        return getScheduledThreadExecutor(DEFAULT_THREAD_NAME);
    }

    public static ScheduledThreadPoolExecutor getScheduledThreadExecutor(Class<?> clazz) {
        return getScheduledThreadExecutor(clazz.getSimpleName() + SUFFIX_POOL);
    }

    public static ScheduledThreadPoolExecutor getScheduledThreadExecutor(String threadName) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = SCHEDULED_THREAD_MAP.get(threadName);
        if (null == scheduledThreadPoolExecutor) {
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadName).build();
            scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, threadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
            SCHEDULED_THREAD_MAP.put(threadName, scheduledThreadPoolExecutor);
            return scheduledThreadPoolExecutor;
        }
        return scheduledThreadPoolExecutor;
    }
}
