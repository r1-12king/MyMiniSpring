package com.minis.aop;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-17 22:16
 **/
public class DefaultAopProxyFactory implements AopProxyFactory{

    @Override
    public AopProxy createAopProxy(Object target, Advisor advisor) {

        return new JdkDynamicAopProxy(target, advisor);
    }
}
