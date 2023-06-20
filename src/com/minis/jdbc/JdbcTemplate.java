package com.minis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @description: jdbc template
 * @author: luguilin
 * @date: 2023-06-20 10:44
 **/
public abstract class JdbcTemplate {

    public JdbcTemplate() {

    }

    public Object query(String sql){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object obj = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC","root","271828lgl");
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            obj = doInStatement(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                rs.close();
                stmt.close();
                con.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }


    /**
     * 模板方法
     *      业务逻辑  - 自己实现
     * @param rs
     * @return
     */
    protected abstract Object doInStatement(ResultSet rs);
}
