package com.test.impl;

import com.minis.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
* @description:
* @author: luguilin
* @date: 2023-12-25 09:59
**/
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("----------my interceptor after method call----------");
    }
}
