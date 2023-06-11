package com.minis.web;

import com.minis.context.ApplicationContext;

import javax.servlet.ServletContext;


/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-11 19:31
 **/
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * 获取上下文
     *
     * @return
     */
    ServletContext getServletContext();

    /**
     * 设置上下文
     *
     * @param servletContext
     */
    void setServletContext(ServletContext servletContext);
}