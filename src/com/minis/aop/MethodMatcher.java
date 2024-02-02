package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @description:方法的匹配算法
 * @author: luguilin
 * @date: 2024-02-02 11:29
 **/
public interface MethodMatcher {
    /**
     * 判断方法是否匹配
     *
     * @param method
     * @param targetCLass
     * @return
     */
    boolean matches(Method method, Class targetCLass);
}
