package com.minis.beans.factory.support;


import com.minis.beans.factory.config.BeanDefinition;

/**
 * @author: luguilin
 * @date: 2023/6/1 22:19
 * @description:
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}