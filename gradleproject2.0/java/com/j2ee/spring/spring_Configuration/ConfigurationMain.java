package com.j2ee.spring.spring_Configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zjm on 2018/10/25.
 */
public class ConfigurationMain {
    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext (ApplicationConfig.class);
        OrderService orderService = ctx.getBean(OrderService.class);
        System.out.println(orderService.getName("调用orderService类的getName函数"));
    }
}
