package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luguilin
 * @date: 2023/5/23 22:50
 * @description:
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    //容器中存放所有bean的名称的列表
    protected List<String> beanNames = new ArrayList<>();

    //容器中存放所有bean实例的map
    protected final Map<String, Object> singletons = new ConcurrentHashMap<>();


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized(this.singletons){
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames(String beanName) {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName){
        synchronized(this.singletons){
            this.singletons.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

}
