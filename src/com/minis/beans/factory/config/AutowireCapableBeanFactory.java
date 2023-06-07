package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.beans.factory.support.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.support.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: luguilin
 * @date: 2023/6/1 22:19
 * @description: 用一个列表 beanPostProcessors 记录所有的 Bean 处理器，这样可以按照需求注册若干个不同用途的处理器，然后调用处理器。
 */
public interface AutowireCapableBeanFactory  extends BeanFactory{
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    /**
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}