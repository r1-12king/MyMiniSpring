package com.minis.web;


/**
 * @description: 用于存储映射关系的实体类
 * @author：luguilin
 * @date:2023/6/9
 */
public class MappingValue {
    String uri;
    String clz;
    String method;

    public MappingValue(String uri, String clz, String method) {
        this.uri = uri;
        this.clz = clz;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}