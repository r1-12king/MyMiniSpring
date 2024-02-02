package com.minis.aop;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-17 22:15
 **/
public interface AopProxyFactory {

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    AopProxy createAopProxy(Object target, PointcutAdvisor adviseor);
}
