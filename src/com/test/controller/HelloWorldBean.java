package com.test.controller;


import com.minis.beans.factory.annotation.Autowired;
import com.minis.web.RequestMapping;
import com.minis.web.bind.annotation.ResponseBody;
import com.test.IAction;
import com.test.entity.User;
import com.test.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-09 11:16
 **/
public class HelloWorldBean {

    @Autowired
    private UserService userService;

    public String doGet() {
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }

    @RequestMapping("/test")
    public String doTest() {
        return "hello world for doGet!";
    }

    @RequestMapping("/test2")
    public String doTest2(User user) {
        System.out.println(user);
        return "hello world for doGet!";
    }

    @RequestMapping("/test7")
    @ResponseBody
    public User doTest7(User user) {
        user.setName(user.getName() + "---");
        user.setBirth(new Date());
        return user;
    }

    @RequestMapping("/test8")
    public User doTest8() {
        User user = userService.getUserInfo(1);
        return user;
    }

    @RequestMapping("/test9")
    @ResponseBody
    public List<User> doTest9() {
        List<User> users = userService.getLargerUserInfo(1);
        return users;
    }

    @RequestMapping("/test10")
    @ResponseBody
    public User doTest10() {
        User users = userService.getUserInfoByMbatis(1);
        return users;
    }

    @Autowired
    IAction action;

    @RequestMapping("/test-aop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {
        action.doAction();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}