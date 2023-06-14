package com.test.entity;

import java.util.Date;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-14 11:13
 **/
public class User {
    private String name;

    private Integer id;

    private Date birth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", Birth=" + birth +
                '}';
    }
}
