package com.minis.web;

import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.support.BeanFactoryPostProcessor;
import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.context.AbstractApplicationContext;
import com.minis.context.ApplicationEvent;
import com.minis.context.ApplicationEventPublisher;
import com.minis.context.ApplicationListener;
import com.minis.context.SimpleApplicationEventPublisher;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @description: AnnotationConfigWebApplicationContext类其实质就是我们 IoC 容器中的 ClassPathXmlApplicationContext，
 *              只是在此基础上增加了  servletContext 的属性，这样就成了一个适用于 Web 场景的上下文。
 * @author: luguilin
 * @date: 2023-06-07 16:31
 **/
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    private WebApplicationContext parentApplicationContext;

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList();

    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.servletContext = this.parentApplicationContext.getServletContext();
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = scanPackages(packageNames);
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        this.beanFactory = bf;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
        loadBeanDefinitions(controllerNames);
        if (true) {
            try {
                refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    public void setParent(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
    }

    public void loadBeanDefinitions(List<String> controllerNames) {
        for (String controller : controllerNames) {
            String beanID = controller;
            String beanClassName = controller;
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

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
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {

    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void finishRefresh() {

    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }
}