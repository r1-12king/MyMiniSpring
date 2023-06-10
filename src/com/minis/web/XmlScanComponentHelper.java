
package com.minis.web;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @description: 加载配置文件的接口类
 * @author: luguilin
 * @date: 2023-06-10 22:31
 **/
public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            //加载配置文件
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Iterator it = root.elementIterator();
        //得到XML中所有的base-package节点
        while (it.hasNext()) {
            Element element = (Element) it.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }
}