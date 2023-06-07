package com.minis.context;


/**
 * @author: luguilin
 * @date: 2023/6/1 22:04
 * @description:
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}