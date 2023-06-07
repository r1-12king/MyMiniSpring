package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

/**
 * @author: luguilin
 * @date: 2023/6/6 18:19
 * @description:
 */
public interface BeanPostProcessor {
    /**
     * bean 初始化前的处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean 初始化后的处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    void setBeanFactory(BeanFactory beanFactory);
}