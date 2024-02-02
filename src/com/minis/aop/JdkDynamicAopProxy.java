package com.minis.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-17 22:18
 **/
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    Object target;

    PointcutAdvisor advisor;

    public JdkDynamicAopProxy(Object target, PointcutAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        System.out.println("----------Proxy new psroxy instance for  ---------" + target);
        System.out.println("----------Proxy new psroxy instance classloader ---------" + JdkDynamicAopProxy.class.getClassLoader());
        System.out.println("----------Proxy new psroxy instance interfaces  ---------" + Arrays.toString(target.getClass().getInterfaces()));

        Object obj = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        // 注意，这里的obj是一个代理对像
        System.out.println("----------Proxy new psroxy instance created r ---------" + obj);
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = (target != null ? target.getClass() : null);
        if (this.advisor.getPointcut().getMethodMatcher().matches(method, targetClass)) {
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            return interceptor.invoke(invocation);
        }
        return null;
    }
}
