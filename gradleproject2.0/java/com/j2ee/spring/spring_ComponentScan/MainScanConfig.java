package com.j2ee.spring.spring_ComponentScan;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value="com.j2ee.spring.spring_ComponentScan")
@Configuration
public class MainScanConfig {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext= new AnnotationConfigApplicationContext(MainScanConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}