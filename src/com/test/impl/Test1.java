package com.test.impl;

import com.minis.beans.BeansException;
import com.minis.beans.ClassPathXmlApplicationContext;
import com.test.AService;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/minis/beans/beans.xml");
        AService aService = (AService)ctx.getBean("aservice");
        BaseService bService = (BaseService)ctx.getBean("baseservice");
        bService.sayHello();
    } 
}