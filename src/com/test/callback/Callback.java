package com.test.callback;


/**
 * @description: 回调接口，用于回调函数的定义，回调函数的实现在调用者中实现，回调函数的调用在被调用者中实现
 * @author: luguilin
 * @date: 2023-06-20 10:44
 **/
public interface Callback {
    void call();
}