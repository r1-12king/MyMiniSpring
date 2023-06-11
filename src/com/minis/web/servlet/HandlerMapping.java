package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;


/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-11 21:35
 **/
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}