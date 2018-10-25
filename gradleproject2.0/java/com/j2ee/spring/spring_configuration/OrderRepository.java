package com.j2ee.spring.spring_configuration;

/**
 * Created by zjm on 2018/10/25.
 */
public class OrderRepository {
    public OrderRepository() {
        System.out.println("初始化OrderRepository");
    }

    public String getName(String name){
      return  "OrderRepository:"+name;
    }
}
