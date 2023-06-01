package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luguilin
 * @date: 2023/5/23 21:39
 * @description: 把 ClassPathXmlApplicationContext 中有关 BeanDefinition 实例化以及加载到内存中的相关内容提取出来
 *
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public SimpleBeanFactory(){

    }

    /**
     * 容器的核心方法
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if(singleton == null){
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
            if(beanDefinition == null){
                // 初始化的时候会初始化 beanName
                throw new BeansException();
            }
                try{
                    singleton = Class.forName(beanDefinition.getClassName());
                }catch (Exception e){
                    e.printStackTrace();
                }
                this.registerSingleton(beanName, singleton);

        }
        return singleton;
    }

    @Override
    public Boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }
}
