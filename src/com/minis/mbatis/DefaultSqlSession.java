package com.minis.mbatis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-10-16 22:33
 **/
public class DefaultSqlSession implements SqlSession {

    JdbcTemplate jdbcTemplate;

    SqlSessionFactory sqlSessionFactory;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback) {
        System.out.println(sqlid);
        String sql = this.sqlSessionFactory.getMapperNode(sqlid).getSql();
        System.out.println(sql);

        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    private void buildParameter() {
    }

    private Object resultSet2Obj() {
        return null;
    }
}
