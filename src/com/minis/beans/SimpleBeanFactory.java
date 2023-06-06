package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luguilin
 * @date: 2023/5/23 21:39
 * @description: 把 ClassPathXmlApplicationContext 中有关 BeanDefinition 实例化以及加载到内存中的相关内容提取出来
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {

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
        if (singleton == null) {
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                // 初始化的时候会初始化 beanName
                throw new BeansException();
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName());
            } catch (Exception e) {
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

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }


    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
           clz = Class.forName(beanDefinition.getClassName());
           ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
           if(!argumentValues.isEmpty()){
               Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
               Object[] paramValues = new Object[argumentValues.getArgumentCount()];
               for (int i=0; i<argumentValues.getArgumentCount(); i++){
                   ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                   if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                       paramTypes[i] = String.class;
                       paramValues[i] = argumentValue.getValue();
                   } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                       paramTypes[i] = Integer.class;
                       paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                   } else if ("int".equals(argumentValue.getType())) {
                       paramTypes[i] = int.class;
                       paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                   } else { //默认为string
                       paramTypes[i] = Class.forName(argumentValue.getType());
                       paramValues[i] = argumentValue.getValue();
                   }
               }
               try { //按照特定构造器创建实例
                   con = clz.getConstructor(paramTypes);
                   obj = con.newInstance(paramValues);
               }catch(Exception e){
                   e.printStackTrace();
               }
           }else{
               obj = clz.newInstance();
           }
        }catch(Exception e){
            e.printStackTrace();
        }

        // 处理属性

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                Class<?>[] paramTypes = new Class<?>[1];
                if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                    paramTypes[0] = String.class;
                } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                    paramTypes[0] = Integer.class;
                } else if ("int".equals(pType)) {
                    paramTypes[0] = int.class;
                } else {
                    // 默认为string
                    paramTypes[0] = String.class;
                }
                Object[] paramValues = new Object[1];
                paramValues[0] = pValue;
                String meathodName = "set" + pName.substring(0, 1).toUpperCase(Locale.ROOT) + pName.substring(1);
                try {
                    Method method = clz.getMethod(meathodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}
