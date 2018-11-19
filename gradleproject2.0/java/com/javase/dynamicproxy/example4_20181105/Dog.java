package com.javase.dynamicproxy.example4_20181105;

/**
 * Created by zjm on 2018/11/5.
 */
public class Dog implements Animal {
    @Override
    public String getName() {
        System.out.println("调用被代理对象dog实例方法");
        return "dog animal";
    }
}
