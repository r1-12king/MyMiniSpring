package com.minis.web.servlet;


/**
 * @description: 由它来根据某个规则或者是用户配置来确定 View 在哪里，下面是它的定义。
 * @author: luguilin
 * @date: 2023-06-15 16:19
 **/
public interface ViewResolver {

    /**
     * 根据 View 的名字找到实际的 View
     *
     * @param viewName
     * @return
     * @throws Exception
     */
    View resolveViewName(String viewName) throws Exception;
}