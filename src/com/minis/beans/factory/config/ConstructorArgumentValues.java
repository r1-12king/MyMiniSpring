package com.minis.beans.factory.config;


import java.util.*;

/**
 * @author: luguilin
 * @date: 2023/6/1 22:19
 * @description:
 */
public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<>();
    public ConstructorArgumentValues() {
    }
    public void addArgumentValue(ConstructorArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }
    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.argumentValueList.get(index);
    }
    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }
    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }
}