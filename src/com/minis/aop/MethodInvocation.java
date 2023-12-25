package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @description: MethodInvocation 实际上就是以前通过反射方法调用业务逻辑的那一段代码的包装
 * @author: luguilin
 * @date: 2023-12-20 18:16
 **/
public interface MethodInvocation {
    /**
     *  获取被调用的方法
     * @return
     */
    Method getMethod();

    /**
     *  获取被调用的方法的参数
     * @return
     */
    Object[] getArguments();

    /**
     *  获取被代理的对象
     * @return
     */
    Object getThis();

    /**
     *  执行方法调用
     * @return
     * @throws Throwable
     */
    Object proceed() throws Throwable;
}
