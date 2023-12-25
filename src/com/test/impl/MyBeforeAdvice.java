package com.test.impl;

import com.minis.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-25 09:59
 **/
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("----------my interceptor before method call----------");
    }
}
