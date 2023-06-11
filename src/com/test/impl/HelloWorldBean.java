package com.test.impl;


import com.minis.web.RequestMapping;

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
}