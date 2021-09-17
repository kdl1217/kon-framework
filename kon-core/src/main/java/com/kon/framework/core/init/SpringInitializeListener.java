package com.kon.framework.core.init;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring初始化监听
 *
 * @author Kong, created on 2020-12-15T11:39.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
@Component
public class SpringInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, SpringInitialize> beansMap = event.getApplicationContext().getBeansOfType(SpringInitialize.class);
        beansMap.values().forEach(bean -> {
            try {
                bean.init(event);
            } catch (Exception e) {
                log.error("SpringInitializeListener [{}] init failed, shutdown...", bean.getClass(), e);
            }
        });
    }
}
