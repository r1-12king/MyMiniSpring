package com.test;

public class AServiceImpl implements AService {

    /**
     * 构造函数注入
     */
    private String name;

    private int level;


    /**
     * setter注入
     */
    private String property1;

    private String property2;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    } 
} 