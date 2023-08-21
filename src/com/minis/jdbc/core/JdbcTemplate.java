package com.minis.jdbc.core;

import com.minis.jdbc.pool.PooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @description: jdbc template
 * @author: luguilin
 * @date: 2023-06-20 10:44
 **/
public class JdbcTemplate {

    private DataSource dataSource;

    private PooledDataSource pooledDataSource;

    public JdbcTemplate() {

    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Object query(StatementCallback stmtcallback) {
        Connection con = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", "root", "271828lgl");
            stmt = con.createStatement();

            return stmtcallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public Object query(String sql, Object[] args, PreparedStatementCallback pstmtcallback) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(pstmt);
            return pstmtcallback.doInPreparedStatement(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {

            }
        }
        return null;
    }


    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> rowMapperResultSetExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pooledDataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(pstmt);
            rs = pstmt.executeQuery();
            return rowMapperResultSetExtractor.extractData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
