package com.minis.web;

import com.minis.beans.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-11 21:35
 **/
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
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
