package com.javase.dynamicproxy.example1;

/**
 * Created by zjm on 2018/1/13.
 */
public class CalculatorImpl implements  Calculator {
    @Override
    public int add(int a, int b) {
        System.out.println("进入被代理的方法");
        int sum=a+b;
        return sum;
    }
}
