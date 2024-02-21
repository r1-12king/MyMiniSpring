package com.test.impl;

import com.test.AService;

/**
 * @description:
 * @author: luguilin
 * @date: 2024-02-04 14:35
 **/
public class Action2 implements AService {
    @Override
    public void sayHello() {
        System.out.println("hello world");
    }

    @Override
    public void doAnything() {
        System.out.println("do anything");
    }
}
