package com.j2ee.spring.spring_aop.example5_dynaproxy;

import java.lang.reflect.Method;
import java.util.Date;

public class DLogger implements ILogger{
    @Override
    public void start(Method method) {
        System.out.println(new Date()+ method.getName() + " say hello start...");
    }
    @Override
    public void end(Method method) {
        System.out.println(new Date()+ method.getName() + " say hello end");
    }
}