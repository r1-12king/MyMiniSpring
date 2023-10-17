package com.minis.mbatis;

/**
 * @description: SqlSessionFactory
 * @author: luguilin
 * @date: 2023-10-16 22:25
 **/
public interface SqlSessionFactory {

    /**
     * 获取一个sqlSession
     *
     * @return
     */
    SqlSession openSession();

    /**
     * 获取一个mapperNode
     *
     * @param name
     * @return
     */
    MapperNode getMapperNode(String name);

}
