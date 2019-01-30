package com.javase.dynamicproxy_CGLIB.example_20190130;

import com.javase.dynamicproxy_CGLIB.example_20190129.UserServiceCglib;
import com.javase.dynamicproxy_CGLIB.example_20190129.UserServiceImpl;
import org.junit.Test;

public class MainClass {
    @Test
    public void  test1(){
        CalculatorCgLIB cglib = new CalculatorCgLIB();
        CalculatorImpl calculator = (CalculatorImpl) cglib.getInstance(new CalculatorImpl());
        int sum=calculator.add(10,20);
        System.out.println("sum(美分):"+sum);
    }
}