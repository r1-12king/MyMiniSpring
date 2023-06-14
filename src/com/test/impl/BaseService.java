package com.test.impl;

import com.minis.beans.factory.annotation.Autowired;
import com.test.AService;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-06 14:41
 **/
public class BaseService {
    @Autowired
    private BaseBaseService bbs;

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseService() {
    }

    public void sayHello() {
        System.out.println("Base Service says Hello");
        bbs.sayHello();
    }

    public String getHello() {
        return "Base Service get Hello.";
    }
}
