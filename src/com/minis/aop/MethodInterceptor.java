package com.minis.aop;

/**
 * @description: 方法上的拦截器
 * @author: luguilin
 * @date: 2023-12-20 18:14
 **/
public interface MethodInterceptor extends Interceptor{

    /**
     * 方法拦截器
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    Object invoke(MethodInvocation invocation) throws Throwable;
}
