package com.j2ee.spring.spring_aop.example4_staticproxy;

/**
 * 目标类代码
 */
public class Hello implements IHello{
    @Override
    public void sayHello(String str) {
        System.out.println("hello "+str);
    }
    
}