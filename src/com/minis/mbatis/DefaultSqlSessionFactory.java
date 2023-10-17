package com.minis.mbatis;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 默认的SqlSessionFactory
 * @author: luguilin
 * @date: 2023-10-16 22:30
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String mapperLocations;

    Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void init() {
        scanLocation(this.mapperLocations);
        for (Map.Entry<String, MapperNode> entry : this.mapperNodeMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private void scanLocation(String location) {
        String sLocationPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath() +"com/minis/resource/"+ location;
        // URL url = this.getClass().getClassLoader().getResource("/"+location);
        System.out.println("mapper location : " + sLocationPath);
        File dir = new File(sLocationPath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanLocation("com/minis/resource/" + location + "/" + file.getName());
            } else {
                buildMapperNodes("com/minis/resource/" + location + "/" + file.getName());
            }
        }
    }

    private void buildMapperNodes(String filePath) {
        System.out.println(filePath);
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();

            String namespace = rootElement.attributeValue("namespace");

            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()) {
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode selectnode = new MapperNode();
                selectnode.setNamespace(namespace);
                selectnode.setId(id);
                selectnode.setParameterType(parameterType);
                selectnode.setResultType(resultType);
                selectnode.setSql(sql);
                selectnode.setParameter("");

                this.mapperNodeMap.put(namespace + "." + id, selectnode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);

        return newSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }
}
