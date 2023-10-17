package com.minis.mbatis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-16 22:25
 **/
public interface SqlSession {

    /**
     * 设置jdbcTemplate
     *
     * @param jdbcTemplate
     */
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    /**
     * 设置sqlSessionFactory
     *
     * @param sqlSessionFactory
     */
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    /**
     * sql 语句
     *
     * @param sqlid
     * @param args
     * @param pstmtcallback
     * @return
     */
    Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);
}
