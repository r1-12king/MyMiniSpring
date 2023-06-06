package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        handleProperties(beanDefinition, clz, obj);
        return obj;
    }

    /**
     * handle properties
     *
     * @param bd
     * @param clz
     * @param obj
     */
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        System.out.println("handle properties for bean : " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        if (propertyValues != null) {
            if (!propertyValues.isEmpty()) {
                for (int i = 0; i < propertyValues.size(); i++) {
                    PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                    String pName = propertyValue.getName();
                    String pType = propertyValue.getType();
                    Object pValue = propertyValue.getValue();
                    boolean isRef = propertyValue.isRef();
                    Class<?>[] paramTypes = new Class<?>[1];
                    Object[] paramValues = new Object[1];
                    if (!isRef) {
                        if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                            paramTypes[0] = String.class;
                            paramValues[0] = pValue;
                        } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                            paramTypes[0] = Integer.class;
                            paramValues[0] = Integer.valueOf((String) pValue);
                        } else if ("int".equals(pType)) {
                            paramTypes[0] = int.class;
                            paramValues[0] = Integer.valueOf((String) pValue).intValue();
                        } else {
                            paramTypes[0] = String.class;
                            paramValues[0] = pValue;
                        }
                    } else { //is ref, create the dependent beans
                        try {
                            paramTypes[0] = Class.forName(pType);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            paramValues[0] = getBean((String) pValue);
                        } catch (BeansException e) {
                            e.printStackTrace();
                        }
                    }
                    String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                    Method method = null;
                    try {
                        method = clz.getMethod(methodName, paramTypes);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    try {
                        method.invoke(obj, paramValues);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
