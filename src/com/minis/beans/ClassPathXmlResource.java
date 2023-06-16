package com.minis.beans;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author: luguilin
 * @date: 2023/5/23 21:30
 * @description:
 */
public class ClassPathXmlResource implements Resource {

    Document document;
    Element rootElement;
    Iterator elementIterator;

    /**
     * 获取新买了类型的资源
     *
     * @param fileName
     */
    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        //将配置文件装载进来，生成一个迭代器，可以用于遍历
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
