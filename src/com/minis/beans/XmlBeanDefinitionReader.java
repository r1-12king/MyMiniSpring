package com.minis.beans;

import org.dom4j.Element;

import java.util.List;

/**
 * @author: luguilin
 * @date: 2023/5/23 21:33
 * @description:
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory){
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element)resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            for(Element propertyElement : propertyElements){
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");
                String type = propertyElement.attributeValue("type");
                PVS.addPropertyValue(new PropertyValue(type, name, value));
            }
            beanDefinition.setPropertyValues(PVS);

            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for(Element e : constructorElements){
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String type = e.attributeValue("type");
                AVS.addArgumentValue(new ArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
