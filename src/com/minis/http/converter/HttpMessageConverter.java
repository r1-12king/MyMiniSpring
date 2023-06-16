package com.minis.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-14 18:05
 **/
public interface HttpMessageConverter {

	/**
	 * 让 controller 返回给前端的字符流数据可以进行格式转换
	 *
	 * @param obj
	 * @param response
	 * @throws IOException
	 */
	void write(Object obj, HttpServletResponse response) throws IOException, IllegalAccessException;
}
