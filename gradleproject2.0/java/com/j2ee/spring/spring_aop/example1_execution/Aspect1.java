package com.j2ee.spring.spring_aop.example1_execution;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("Aspect1")
@Aspect
public class Aspect1 {

    @Before(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void before(JoinPoint joinPoint) {
        Object[] objects=joinPoint.getArgs();
        HttpServletRequest request= (HttpServletRequest) objects[0];
        request.getSession().setAttribute("school","清华大学");
        System.out.println("[Aspect1] before advise");
    }

    @Around(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[Aspect1] around advise 1");
        pjp.proceed();
        System.out.println("[Aspect1] around advise2");
    }

    @AfterReturning(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void afterReturning(JoinPoint joinPoint) {
        Object[] objects=joinPoint.getArgs();
        HttpServletRequest request= (HttpServletRequest) objects[0];
        String name=request.getParameter("name");
        String age=request.getParameter("age");
        System.out.println("[Aspect1] afterReturning advise---->name:"+name+";age:"+age);
    }

    @AfterThrowing(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void afterThrowing(JoinPoint joinPoint) {

        System.out.println("[Aspect1] afterThrowing advise");
    }

    @After(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void after(JoinPoint joinPoint) {

        System.out.println("[Aspect1] after advise");
    }
}
