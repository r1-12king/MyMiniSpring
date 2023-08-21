package com.test.impl;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.OldJdbcTemplate;
import com.minis.jdbc.core.RowMapper;
import com.test.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-20 10:59
 **/
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 以前写在 UserJdbcImpl 里的业务代码，也就是对 SQL 语句返回值的处理逻辑，现在成了匿名类，作为参数传入 query() 里，最后在 query() 里会回调到它。
     *
     * @param userid
     * @return
     */
    public User getUserInfo_0(int userid) {
        String sql = "select id, name,birth from user where id="+userid;
        OldJdbcTemplate jdbcTemplate = new UserJdbcImpl();
        User rtnUser = (User)jdbcTemplate.query(sql);

        return rtnUser;
    }

    public User getUserInfo(int userid) {
        final String sql = "select id, name,birth from user where id=" + userid;
        return (User) jdbcTemplate.query((stmt) -> {
            ResultSet rs = stmt.executeQuery(sql);
            User rtnUser = null;
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(userid);
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirth(new java.util.Date(rs.getDate("birth").getTime()));
            }
            return rtnUser;
        });
    }


    public User getUserInfo_2(int userid) {
        final String sql = "select id, name,birthday from users where id=?";
        return (User)jdbcTemplate.query(sql, new Object[]{new Integer(userid)},
                (pstmt)->{
                    ResultSet rs = pstmt.executeQuery();
                    User rtnUser = null;
                    if (rs.next()) {
                        rtnUser = new User();
                        rtnUser.setId(userid);
                        rtnUser.setName(rs.getString("name"));
                    }
                    return rtnUser;
                }
        );
    }


    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserInfo_0(1);
        System.out.println(user);
    }

    public List<User> getLargerUserInfo(int i) {
        final String sql = "select id, name,birth from user where id>?";
        return (List) jdbcTemplate.query(sql, new Object[]{new Integer(i)}, new RowMapper() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirth(new java.util.Date(rs.getDate("birth").getTime()));
                return rtnUser;
            }
        });
    }
}