package com.minis.context;

import com.minis.beans.BeansException;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-15 17:53
 **/
public interface ApplicationContextAware {

	/**
	 *
	 * @param applicationContext
	 * @throws BeansException
	 */
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
