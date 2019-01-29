package com.j2ee.spring.spring_aop.example3_execution_jackson;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component("aspectJackson")
public class AspectJackson {

    //@Before(value = "com.j2ee.spring.spring_aop.example3_execution_jackson.JacksonPointCuts.writeValuePointcut()")
    public void before(JoinPoint joinPoint) {

        System.out.println("[AspectJackson] before advise:"+joinPoint.getTarget());
    }

}
