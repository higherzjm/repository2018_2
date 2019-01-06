package com.j2ee.spring.spring_aop.example1_execution;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(public * com.j2ee.spring.spring_aop.example1_execution.SpringAopMainController.test*(..))")
    public void aopDemo() {
         System.out.println("aopDemo-----------------------------");
    }
}
