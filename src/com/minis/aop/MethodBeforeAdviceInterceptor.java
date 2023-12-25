package com.minis.aop;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-12-21 11:46
 **/
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
