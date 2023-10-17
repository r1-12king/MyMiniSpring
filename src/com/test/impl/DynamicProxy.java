package com.test.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: 动态代理
 * @author: luguilin
 * @date: 2023-10-17 21:19
 **/
public class DynamicProxy {
   private Object subject = null; 
   
   public DynamicProxy(Object subject) {
         this.subject = subject;
   }

    public Object getProxy() {
        return Proxy.newProxyInstance(DynamicProxy.class
                        .getClassLoader(), subject.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("doAction")) {
                            System.out.println("before call real object........");
                            return method.invoke(subject, args);
                        }
                        return null;
                    }
                });
    }
}