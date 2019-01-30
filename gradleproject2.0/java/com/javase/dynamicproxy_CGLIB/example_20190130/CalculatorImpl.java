package com.javase.dynamicproxy_CGLIB.example_20190130;


/**
 * Created by zjm on 2018/1/13.
 */
public class CalculatorImpl {
    public int add(int a, int b) {
        System.out.println("进入被代理的方法");
        int sum=a+b;
        return sum;
    }
}
