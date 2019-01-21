package com.j2ee.spring.spring_aop.example3_execution_jackson;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component("aspectJackson")
public class AspectJackson {
    @Pointcut("execution(public * com.fasterxml.jackson.databind.ObjectMapper.writeValue(..))")
    public void writeValuePointcut() {
        System.out.println("aspectJackson切面");
    }

    @Before(value = "writeValuePointcut()")
    public void before(JoinPoint joinPoint) {

        System.out.println("[AspectJackson] before advise:"+joinPoint.getTarget());
    }

}
