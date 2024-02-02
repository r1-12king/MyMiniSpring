package com.minis.aop;


/**
 * @description: 定义了切点，也就是返回一条匹配规则
 * @author: luguilin
 * @date: 2024-02-02 11:29
 **/
public interface Pointcut {
    /**
     * 返回一条匹配规则
     *
     * @return
     */
    MethodMatcher getMethodMatcher();
}