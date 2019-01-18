package com.j2ee.spring.spring_aop.example3_execution_jackson;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Aspect
@Component("aspectJackson")
public class AspectJackson {
    @Pointcut("execution(* com.fasterxml.jackson.databind.ObjectMapper.writeValue(..))")
    public void writeValuePointcut() {
    }

    @Before(value = "writeValuePointcut()")
    public void before(JoinPoint joinPoint) {

        System.out.println("[AspectJackson] before advise:"+joinPoint.getTarget());
    }

}
