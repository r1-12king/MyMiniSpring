package com.minis.beans;


import com.minis.beans.factory.support.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.support.BeanFactoryPostProcessor;
import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.context.AbstractApplicationContext;
import com.minis.context.ApplicationEvent;
import com.minis.context.ApplicationEventPublisher;
import com.minis.context.ApplicationListener;
import com.minis.context.ContextRefreshEvent;
import com.minis.context.SimpleApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: luguilin
 * @date: 2023/5/21 20:58
 * @description: ClassPathXmlApplicationContext 在实例化的过程中做了三件事。
 * 解析 XML 文件中的内容。
 * 加载解析的内容，构建 BeanDefinition。
 * 读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中。
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Register bean processors that intercept bean creation.
     *
     * @throws BeansException
     * @throws IllegalStateException
     */
//    @Override
//    public void refresh() throws BeansException, IllegalStateException {
//        // Initialize other special beans in specific context subclasses.
//        registerBeanPostProcessors(this.beanFactory);
//        onRefresh();
//    }

    @Override
    protected void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    protected void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {

    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return null;
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }


    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class getType(String name) {
        return null;
    }
}
