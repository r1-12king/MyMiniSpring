package com.test.impl;

import com.minis.jdbc.core.OldJdbcTemplate;
import com.test.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-20 10:56
 **/
public class UserJdbcImpl extends OldJdbcTemplate{
    @Override
    protected Object doInStatement(ResultSet rs) {
        //从jdbc数据集读取数据，并生成对象返回
        User rtnUser = null;
        try {
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirth(new java.util.Date(rs.getDate("birth").getTime()));
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rtnUser;
    }
}
