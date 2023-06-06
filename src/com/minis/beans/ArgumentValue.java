package com.minis.beans;


/**
 * @author: luguilin
 * @date: 2023/6/1 22:15
 * @description:
 */
public class ArgumentValue {
    private String value;
    private String type;
    private String name;

    public ArgumentValue(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(String value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}