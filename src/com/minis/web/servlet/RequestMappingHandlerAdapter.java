package com.minis.web.servlet;

import com.minis.beans.BeansException;
import com.minis.web.WebApplicationContext;
import com.minis.web.WebDataBinder;
import com.minis.web.WebDataBinderFactory;
import com.test.WebBindingInitializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * @description: 实现 HandlerAdapter 接口，主要就是实现 handle() 方法，
 * 基本过程是接受前端传 request、 response 与 handler，通过反射中的 invoke 调用方法并处理返回数据。
 * @author: luguilin
 * @date: 2023-06-11 21:53
 **/
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    WebApplicationContext wac;
    private WebBindingInitializer webBindingInitializer = null;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        try {
            invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webBindingInitializer.initBinder(wdb);
            wdb.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }

        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);

        response.getWriter().append(returnObj.toString());

    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }
}