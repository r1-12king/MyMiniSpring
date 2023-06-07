package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.annotation.Autowired;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-06 18:32
 **/
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields != null) {
            //对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field declaredField : declaredFields) {
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (annotation != null) {
                    String name = declaredField.getName();
                    Object autowiredBean = this.getBeanFactory().getBean(name);
                    if (autowiredBean != null) {
                        declaredField.setAccessible(true);
                        try {
                            declaredField.setAccessible(true);
                            declaredField.set(bean, autowiredBean);
                            System.out.println("autowire " + name + " for bean " + beanName);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
