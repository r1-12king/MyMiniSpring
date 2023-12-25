package com.minis.beans.factory;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-25 11:40
 **/
public interface BeanFactoryAware {
    /**
     *
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);
}
