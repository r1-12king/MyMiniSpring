package com.minis.aop;

import java.util.Arrays;

/**
 * @description: 在 invoke() 中实现自己的业务增强代码。
 * @author: luguilin
 * @date: 2023-12-20 18:17
 **/
public class TracingInterceptor implements MethodInterceptor{

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("method " + invocation.getMethod() + " is called on " +
                invocation.getThis() + " with args " + Arrays.toString(invocation.getArguments()));
        Object ret = invocation.proceed();
        System.out.println("method " + invocation.getMethod() + " returns " + ret);
        return ret;
    }

}
