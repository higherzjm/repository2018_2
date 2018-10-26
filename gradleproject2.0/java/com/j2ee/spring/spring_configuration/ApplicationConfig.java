package com.j2ee.spring.spring_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ApplicationConfig { 
   
        public
        //@Bean
        OrderService orderService() {
                return new OrderService(orderRepository());
        }
        //@Bean
        public
         OrderRepository orderRepository() {
                return new OrderRepository();
        } 
   

} 