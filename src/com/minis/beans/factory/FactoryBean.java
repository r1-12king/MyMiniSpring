package com.minis.beans.factory;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-17 22:18
 **/
public interface FactoryBean<T> {

	/**
	 * 获取对象
	 *
	 * @return
	 * @throws Exception
	 */
	T getObject() throws Exception;

	/**
	 * 获取类名
	 *
	 * @return
	 */
	Class<?> getObjectType();

	/**
	 * 是否单例
	 *
	 * @return
	 */
	default boolean isSingleton() {
		return true;
	}

}