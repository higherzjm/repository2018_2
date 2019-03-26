package com.j2ee.spring.spring_aop.example5_dynaproxy;

import com.j2ee.spring.spring_aop.example4_staticproxy.Hello;
import com.j2ee.spring.spring_aop.example4_staticproxy.IHello;

/**
 * 动态代理2_日志可以更灵活打印
 */
public class DynaTest1 {
    public static void main(String[] args) {
        IHello hello = (IHello) new DynaProxyHello1().bind(new Hello(),new DLogger());//如果我们需要日志功能，则使用代理类
        //IHello hello = new Hello();//如果我们不需要日志功能则使用目标类
        hello.sayHello("明天");
    }
}