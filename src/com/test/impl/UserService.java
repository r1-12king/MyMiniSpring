package com.test.impl;

import com.minis.jdbc.JdbcTemplate;
import com.test.entity.User;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-20 10:59
 **/
public class UserService {
    public User getUserInfo(int userid) {
        String sql = "select id, name,birth from user where id="+userid;
        JdbcTemplate jdbcTemplate = new UserJdbcImpl();
        User rtnUser = (User)jdbcTemplate.query(sql);

        return rtnUser;
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserInfo(1);
        System.out.println(user);
    }
}