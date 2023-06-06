package com.minis.beans.factory.config;

/**
 * @author: luguilin
 * @date: 2023/5/23 22:34
 * @description: 定义一下 SingletonBeanRegistry，将管理单例 Bean 的方法规范好。
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames(String beanName);
}
