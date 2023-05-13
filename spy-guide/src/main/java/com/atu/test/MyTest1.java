package com.atu.test;

/**
 * @author: Tom
 * @create: 2023-05-11 15:31
 * @Description:
 */
public class MyTest1 {
    public MyTest1() {
        System.out.println("MyTest1 is loader by :" + this.getClass().getClassLoader());
        new MyTest2();
    }
}

