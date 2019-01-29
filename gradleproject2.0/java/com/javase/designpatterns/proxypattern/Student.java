package com.javase.designpatterns.proxypattern;

/**
 * Student类实现Person接口。Student可以具体实施上交班费的动作。
 */
public class Student implements Person {
    private String name;
    public Student(String name) {
        this.name = name;
    }
    
    @Override
    public void giveMoney() {
       System.out.println(name + "上交班费50元");
    }
}