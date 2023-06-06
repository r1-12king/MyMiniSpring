package com.minis.beans;


import java.util.*;

/**
 * @author: luguilin
 * @date: 2023/6/1 22:19
 * @description:
 */
public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();
    public ArgumentValues() {
    }
    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }
    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.argumentValueList.get(index);
    }
    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }
    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }
}