package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-21 10:14
 **/
public interface AfterReturningAdvice extends AfterAdvice{

    /**
     * 后置通知
     *
     * @param returnValue
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
