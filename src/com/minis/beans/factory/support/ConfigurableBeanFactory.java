package com.minis.beans.factory.support;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.SingletonBeanRegistry;


/**
 * @author: luguilin
 * @date: 2023/6/7 14:34
 * @description: 定义一下 SingletonBeanRegistry，将管理单例 Bean 的方法规范好。
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 设置bean处理器
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取bean处理器的数量
     *
     * @return
     */
    int getBeanPostProcessorCount();

    /**
     * 注册bean依赖关系
     *
     * @param beanName
     * @param dependentBeanName
     * @return
     */
    void registerDependentBean(String beanName, String dependentBeanName);


    String[] getDependentBeans(String beanName);


    String[] getDependenciesForBean(String beanName);
}