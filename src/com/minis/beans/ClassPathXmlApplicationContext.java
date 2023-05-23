package com.minis.beans;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: luguilin
 * @date: 2023/5/21 20:58
 * @description:
 *      ClassPathXmlApplicationContext 在实例化的过程中做了三件事。
 *          解析 XML 文件中的内容。
 *          加载解析的内容，构建 BeanDefinition。
 *          读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中。
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory factory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = factory;
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
