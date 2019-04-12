package com.j2ee.spring.spring_annotation.Import_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zjm on 2019/4/4.
 */
public class MainClass {
    @Test
    public void  test1(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
