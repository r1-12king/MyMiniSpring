package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-21 10:14
 **/
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 前置通知
     *
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
