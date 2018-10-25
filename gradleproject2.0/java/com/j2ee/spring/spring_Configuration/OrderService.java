package com.j2ee.spring.spring_Configuration;

/**
 * Created by zjm on 2018/10/25.
 */
public class OrderService {
    public  OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        System.out.println("初始化OrderService");
        this.orderRepository = orderRepository;
    }

    public String getName(String name){
        return orderRepository.getName(name);
    }
}
