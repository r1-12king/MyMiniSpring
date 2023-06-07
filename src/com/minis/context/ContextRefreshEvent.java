package com.minis.context;


/**
 * @author: luguilin
 * @date: 2023/6/7 16:03
 * @description:
 */
public class ContextRefreshEvent extends ApplicationEvent{
    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }
    
    @Override
    public String toString() {
        return this.msg;
    }
}