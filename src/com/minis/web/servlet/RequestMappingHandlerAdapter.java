package com.minis.web.servlet;

import com.minis.beans.BeansException;
import com.minis.http.converter.HttpMessageConverter;
import com.minis.web.WebApplicationContext;
import com.minis.web.WebDataBinder;
import com.minis.web.WebDataBinderFactory;
import com.minis.web.bind.annotation.ResponseBody;
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

    /**
     * 增加一个属性 messageConverter，通过它来转换数据
     */
    private HttpMessageConverter messageConverter = null;

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }


    public RequestMappingHandlerAdapter() {

    }

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        ModelAndView mv = null;
        try {
            mv = invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            if (methodParameter.getType()!=HttpServletRequest.class && methodParameter.getType()!=HttpServletResponse.class) {
                Object methodParamObj = methodParameter.getType().newInstance();
                WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
                webBindingInitializer.initBinder(wdb);
                wdb.bind(request);
                methodParamObjs[i] = methodParamObj;
            }
            else if (methodParameter.getType()==HttpServletRequest.class) {
                methodParamObjs[i] = request;
            }
            else if (methodParameter.getType()==HttpServletResponse.class) {
                methodParamObjs[i] = response;
            }
            i++;
        }


        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        Class<?> returnType = invocableMethod.getReturnType();

        ModelAndView mav = null;

        //ResponseBody 注解的方法，返回值转成字符串，写入 response
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)){
            this.messageConverter.write(returnObj, response);
        }else if (returnType == void.class) {
        }
        else {
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView)returnObj;
            }
            else if(returnObj instanceof String) {
                String sTarget = (String)returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }
        return mav;
    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }
}