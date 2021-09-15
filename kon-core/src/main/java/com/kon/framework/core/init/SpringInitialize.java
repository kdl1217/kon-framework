package com.kon.framework.core.init;

import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Spring初始化接口，实现该接口的子类会被扫描并执行init方法。
 *      当init执行失败后，会执行destroy方法。
 *
 * @author Kong, created on 2020-12-15T11:06.
 * @version 1.0.0-SNAPSHOT
 */
public interface SpringInitialize {

    /**
     * 初始化方法
     * @param event 事件
     */
    void init(ContextRefreshedEvent event) throws InterruptedException;

    /**
     * 销毁方法
     */
    default void destroy() {}
}
