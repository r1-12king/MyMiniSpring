package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {

	void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
