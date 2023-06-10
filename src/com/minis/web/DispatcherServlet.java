package com.minis.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final List<String> packageNames = new ArrayList<>();
    private final Map<String, Object> controllerObjs = new HashMap<>();
    private final Map<String, Class<?>> controllerClasses = new HashMap<>();
    private final List<String> urlMappingNames = new ArrayList<>();
    /**
     * url 和 方法的映射关系
     */
    private final Map<String, Method> mappingMethods = new HashMap<>();
    private final Map<String, Class<?>> mappingClz = new HashMap<>();
    /**
     * url 和 对象的映射关系
     */
    private final Map<String, Object> mappingObjs = new HashMap<>();
    private String sContextConfigLocation;
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, MappingValue> mappingValues;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(rs);
        Refresh();
    }

    protected void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sPath = request.getServletPath();
        System.out.println(sPath);
        if (this.mappingValues.get(sPath) == null) { return; }

        //获取bean类定义
        Class<?> clz = this.mappingClz.get(sPath);
        //获取bean实例
        Object obj = this.mappingObjs.get(sPath);
        //获取调用方法名
        String methodName = this.mappingValues.get(sPath).getMethod();
        Object objResult = null;
        try {
            Method method = clz.getMethod(methodName);
            //方法调用
            objResult = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将方法返回值写入response
        assert objResult != null;
        response.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}