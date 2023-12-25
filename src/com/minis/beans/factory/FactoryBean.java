package com.minis.beans.factory;

/**
 * @description: FactoryBean在Spring中的作用
 * 当配置文件中< bean>的 class 属性配置的实现类是 FactoryBean 时，通过 getBean()方法返回的不是 FactoryBean 本身，而是 FactoryBean#getObject() 方法所返回的对象。相当于FactoryBean#getObject()代理了 getBean()方法。
 * @author: luguilin
 * @date: 2023-10-17 22:18
 **/
public interface FactoryBean<T> {

	/**
	 * 获取对象 返回由 FactoryBean 创建的 bean 实例，如果 isSingleton()返回 true，则该实例会放到 Spring 容器中单实例缓存池中。
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
	 * 是否单例 -- 返回由 FactoryBean 创建的 bean 实例的作用域是 singleton 还是prototype。 默认为 true，即为单例。
	 *
	 * @return
	 */
	default boolean isSingleton() {
		return true;
	}

}