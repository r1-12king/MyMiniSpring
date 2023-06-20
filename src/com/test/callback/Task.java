package com.test.callback;


/**
 * @description: 任务 -把一个回调函数作为参数传给了调用者，调用者在执行完自己的任务后调用这个回调函数。
 * @author: luguilin
 * @date: 2023-06-20 10:44
 **/
public class Task {
    public void executeWithCallback(Callback callback) {
        execute(); //具体的业务逻辑处理
        if (callback != null) {
            callback.call();
        }
    }


    public static void main(String[] args) {
        Task task = new Task();
        Callback callback = new Callback() {
            @Override
            public void call() {
                System.out.println("callback...");
            }
        };
        task.executeWithCallback(callback);
    }

    public void execute(){
        System.out.println("execute...");
    }
}