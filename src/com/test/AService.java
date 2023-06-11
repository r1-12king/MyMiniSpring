package com.test;

import com.test.impl.AServiceImpl;

public interface AService {
    void sayHello();

    class BaseBaseService {
        private AServiceImpl as;

        public AServiceImpl getAs() {
            return as;
        }

        public void setAs(AServiceImpl as) {
            this.as = as;
        }

        public void sayHello() {
            System.out.println("Base Base Service says Hello");
        }
    }
}