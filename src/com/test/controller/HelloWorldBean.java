package com.test.controller;


import com.minis.web.RequestMapping;
import com.test.entity.User;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-09 11:16
 **/
public class HelloWorldBean {
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
}