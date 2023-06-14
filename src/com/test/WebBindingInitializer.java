package com.test;

import com.minis.web.WebDataBinder;

/**
 * @author admin
 */
public interface WebBindingInitializer {
    /**
     * 初始化binder
     * @param binder
     */
    void initBinder(WebDataBinder binder);
}