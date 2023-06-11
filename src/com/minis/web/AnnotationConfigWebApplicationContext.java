package com.minis.web;

import com.minis.beans.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;


/**
 * @description: AnnotationConfigWebApplicationContext类其实质就是我们 IoC 容器中的 ClassPathXmlApplicationContext，
 *              只是在此基础上增加了  servletContext 的属性，这样就成了一个适用于 Web 场景的上下文。
 * @author: luguilin
 * @date: 2023-06-07 16:31
 **/
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}