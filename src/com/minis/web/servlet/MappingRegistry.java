package com.minis.web.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-11 21:50
 **/
public class MappingRegistry {
    /**
     * 存储访问的url名称
     */
    private List<String> urlMappingNames = new ArrayList<>();

    /**
     * 存储访问的url名称 和 Bean 实例
     */
    private Map<String, Object> mappingObjs = new HashMap<>();

    /**
     * 存储访问的url名称 和 方法对象
     */
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}