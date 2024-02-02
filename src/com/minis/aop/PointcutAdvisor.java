package com.minis.aop;

/**
 * @description: 能支持切点 Pointcut
 * @author: luguilin
 * @date: 2024-02-02 11:29
 **/
public interface PointcutAdvisor extends Advisor {

    /**
     * 扩展了 Advisor，内部可以返回 Pointcut
     *
     * @return
     */
    Pointcut getPointcut();
}