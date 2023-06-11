package com.minis.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @description: Servlet 服务器 Tomcat创建 listener 中定义的监听类的实例
 * @author: luguilin
 * @date: 2023-06-11 19:31
 **/
public class ContextLoaderListener implements ServletContextListener {
    /**
     * 默认值是 contextConfigLocation，这是代表配置文件路径的一个变量，也就是 IoC 容器的配置文件
     */
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    /**
     * 监听器初始化方法是 contextInitialized(ServletContextEvent event)
     * 初始化方法中可以通过 event.getServletContext().getInitParameter(“name”) 方法获得上下文环境中的键值对--全局参数
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        WebApplicationContext wac = new XmlWebApplicationContext(sContextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }
}