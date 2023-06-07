package com.test;

import com.minis.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-06 14:41
 **/
public class BaseService {
//    @Autowired
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
}
