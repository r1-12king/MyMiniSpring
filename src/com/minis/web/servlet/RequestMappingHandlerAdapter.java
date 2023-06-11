package com.minis.web.servlet;

import com.minis.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


/**
 * @description: 实现 HandlerAdapter 接口，主要就是实现 handle() 方法，
 *                基本过程是接受前端传 request、 response 与 handler，通过反射中的 invoke 调用方法并处理返回数据。
 * @author: luguilin
 * @date: 2023-06-11 21:53
 **/
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object objResult = null;
        try {
            objResult = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
          assert objResult != null;
          response.getWriter().append(objResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}