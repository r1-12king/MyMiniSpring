package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

import java.util.Map;


/**
 * @author: luguilin
 * @date: 2023/6/7 14:34
 * @description: 定义一下 SingletonBeanRegistry，将管理单例 Bean 的方法规范好。
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 是否存在beanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取beanDefinition的数量
     *
     * @return
     * @throws BeansException
     */
    int getBeanDefinitionCount();

    /**
     * 获取所有beanDefinition的名称
     *
     * @return
     * @throws BeansException
     */
    String[] getBeanDefinitionNames();

    /**
     * 通过类型获取所有beanDefinition的名称
     *
     * @param type
     * @return
     * @throws BeansException
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 通过类型获取所有beanDefinition的名称和类型的映射
     *
     * @param type
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}