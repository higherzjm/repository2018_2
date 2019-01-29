package com.j2ee.spring.spring_aop.example3_execution_jackson;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by zjm on 2019/1/21.
 */
public class JacksonPointCuts {
    @Pointcut("execution(public * com.fasterxml.jackson.databind.ObjectMapper.writeValue(..))")
    public void writeValuePointcut() {
        System.out.println("aspectJackson切面");
    }
}
