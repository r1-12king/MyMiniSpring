package com.minis.context;


import java.util.EventListener;

/**
 * @author: luguilin
 * @date: 2023/6/7 22:03
 * @description:
 */
public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}