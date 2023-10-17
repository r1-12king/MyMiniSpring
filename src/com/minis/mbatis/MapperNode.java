package com.minis.mbatis;

/**
 * @description: mapper 文件映射对象
 * @author: luguilin
 * @date: 2023-10-16 22:14
 **/
public class MapperNode {

    String namespace;
    String id;
    String parameterType;
    String resultType;
    String sql;
    String parameter;

    public String getNamespace() {
        return namespace;
    }

    public String getId() {
        return id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public String getSql() {
        return sql;
    }

    public String getParameter() {
        return parameter;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return this.namespace+"."+this.id+" : " +this.sql;
    }
}
