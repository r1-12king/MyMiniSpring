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
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result,  beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }
}