package com.minis.beans;


/**
 * @author: luguilin
 * @date: 2023/6/1 22:17
 * @description:
 */
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;

    public PropertyValue(String type, String name, Object value) {

        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}