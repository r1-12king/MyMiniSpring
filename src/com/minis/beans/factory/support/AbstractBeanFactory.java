package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.FactoryBean;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements BeanDefinitionRegistry, ConfigurableBeanFactory {
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    protected List<String> beanDefinitionNames = new ArrayList<>();

    public AbstractBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Refresh 调用结束");
    }

    /**
     * 先获取 Bean 的定义，然后创建 Bean 实例，再进行 Bean 的后处理并初始化。在这个抽象类里，我们需要关注两个核心的改动。
     * 定义了抽象方法 applyBeanPostProcessorBeforeInitialization 与 applyBeanPostProcessorAfterInitialization，
     * 由名字可以看出，分别是在 Bean 处理类初始化之前和之后执行的方法。
     * 这两个方法交给具体的继承类去实现。在 getBean() 方法中，在以前预留的位置，实现了对 Bean 初始化前、初始化和初始化后的处理。
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        //先尝试直接从容器中获取bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            //如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                //如果连毛胚都没有，则创建bean实例并注册
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition != null) {
                    singleton = createBean(beanDefinition);
                    this.registerBean(beanName, singleton);

                    if (singleton instanceof BeanFactoryAware) {
                        ((BeanFactoryAware) singleton).setBeanFactory(this);
                    }

                    // 进行beanpostprocessor处理
                    // step 1: postProcessBeforeInitialization
                    applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                    // step 2: init-method
                    if (beanDefinition.getInitMethodName() != null && !beanDefinition.equals("")) {
                        invokeInitMethod(beanDefinition, singleton);
                    }
                    // step 3: postProcessAfterInitialization
                    applyBeanPostProcessorsAfterInitialization(singleton, beanName);
                }else{
                    return null;
                }
            }
        } else {
            System.out.println("bean exist -------------- " + beanName + "----------------" + singleton);
        }
        //        if (singleton == null) {
        //        	throw new BeansException("bean is null.");
        //        }

        //process Factory Bean
        if (singleton instanceof FactoryBean) {
            System.out.println("factory bean -------------- " + beanName + "----------------" + singleton);
            return this.getObjectForBeanInstance(singleton, beanName);
        } else {
            System.out.println("normal bean -------------- " + beanName + "----------------" + singleton);

        }

        return singleton;
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class<?> clz = obj.getClass();
        clz =  obj.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }


    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition
            beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        //创建毛胚bean实例  通过构造函数创建Bean实例--此时还没有进行属性处理
        Object obj = doCreateBean(beanDefinition);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //完善bean，主要是处理属性
        populateBean(beanDefinition, clz, obj);
        return obj;
    }

    //doCreateBean创建毛胚实例，仅仅调用构造方法，没有进行属性处理
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // handle constructor
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (constructorArgumentValues !=null && !constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf(constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf(constructorArgumentValue.getValue());
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                try {
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // handle properties
        System.out.println("handle properties for bean : " + beanDefinition.getId());
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        //如果有属性
        if (propertyValues !=null && !propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue =
                        propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) { //如果不是ref，只是普通属性
                    //对每一个属性，分数据类型分别处理
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                        paramValues[0] = pValue;
                    }
                    else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                        paramValues[0] = Integer.valueOf((String) pValue);
                    }
                    else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                        paramValues[0] = Integer.valueOf((String) pValue);
                    }
                    else {
                        paramTypes[0] = String.class;
                        paramValues[0] = pValue;
                    }
                } else {//is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                        //再次调用getBean创建ref的bean实例
                        paramValues[0] = getBean((String) pValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // Now we have the bean instance, which may be a normal bean or a FactoryBean.
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = null;
        FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
        object = getObjectFromFactoryBean(factory, beanName);
        return object;
    }

    abstract public void applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public void applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;


}