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
 */
public class ClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();


    /**
     * 构造器获取外部配置，解析出Bean 的定义，行成内存映像
     *
     * @param fileName
     */
    public ClassPathXmlApplicationContext(String fileName){
        this.readXml(fileName);
        this.instanceBeans();
    }

    /**
     * 获取新买了配置文件信息
     *
     * @param fileName
     */
    private void readXml(String fileName){
        SAXReader saxReader = new SAXReader();
        try{
            // this.getClass().getClassLoader().getResource()只能接受一个相对路径，不能接收绝对路径如/xxx/xxx。并且，接收的相对路径是相对于项目的包的根目录来说的。
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();

            // 对配置文件的每一个bean进行处理
            for(Element element: (List<Element>) rootElement.elements()){
                // 获取Bean信息
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
                // 将bean 的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射获取bean实例，并存储在map 中
     */
    private void instanceBeans(){
        for(BeanDefinition beanDefinition: beanDefinitions){
            try{
                singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        return singletons.get(beanName);
    }
}
