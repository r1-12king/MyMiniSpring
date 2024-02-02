package com.minis.aop;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-20 18:27
 */
public interface Advisor {

    /**
     * 获取方法拦截器
     *
     * @return
     */
    MethodInterceptor getMethodInterceptor();

    /**
     * 设置方法拦截器
     *
     * @param methodInterceptor
     */
    void setMethodInterceptor(MethodInterceptor methodInterceptor);

    /**
     * 获取一个advice
     *
     * @return
     */
    Advice getAdvice();
}
