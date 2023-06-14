package com.test.impl;

import com.minis.web.WebDataBinder;
import com.test.WebBindingInitializer;

import java.util.Date;

/**
 * @author admin
 */
public class DateInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }
}
