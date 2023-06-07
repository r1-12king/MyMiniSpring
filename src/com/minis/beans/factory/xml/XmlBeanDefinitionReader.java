package com.minis.beans.factory.xml;

import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;
import com.minis.beans.Resource;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.beans.factory.support.SimpleBeanFactory;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: luguilin
 * @date: 2023/5/23 21:33
 * @description:
 */
public class XmlBeanDefinitionReader {

    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element)resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for(Element propertyElement : propertyElements){
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");
                String type = propertyElement.attributeValue("type");
                String pRef = propertyElement.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if(value!=null && !value.equals("")) {
                    isRef = false;
                    pV = value;
                }else if(pRef!=null && !pRef.equals("")){
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(type, name, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            // 设置依赖关系
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for(Element e : constructorElements){
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String type = e.attributeValue("type");
                AVS.addArgumentValue(new ConstructorArgumentValue(value, type, name));
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
        System.out.println("BeanDefinition加载完毕");
    }
}
