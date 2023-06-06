package com.test;

import com.minis.beans.BeansException;
import com.minis.beans.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/minis/beans/beans.xml");
        AService aService = (AService)ctx.getBean("aservice");
        aService.sayHello();
    } 
}