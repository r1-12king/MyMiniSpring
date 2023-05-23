package com.minis.beans;


/**
 * @author: luguilin
 * @date: 2023/5/23 20:58
 * @description:
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}