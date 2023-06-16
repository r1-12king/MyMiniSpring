package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-15 16:19
 **/
public interface View {
    /**
     * 获取 HTTP 请求的 request 和 response，以及中间产生的业务数据 Model，最后写到 response 里面。request 和 response 是 HTTP 访问时由服务器创建的，ModelAndView 是由我们的 MiniSpring 创建的
     *
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    default String getContentType() {
        return null;
    }

    void setContentType(String contentType);

    String getUrl();

    void setUrl(String url);

    String getRequestContextAttribute();

    void setRequestContextAttribute(String requestContextAttribute);
}
