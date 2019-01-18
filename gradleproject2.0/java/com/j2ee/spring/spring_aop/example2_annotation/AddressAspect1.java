package com.j2ee.spring.spring_aop.example2_annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zjm on 2019/1/6.
 */
@Component("AddressAspect1")
@Aspect
public class AddressAspect1 {
    @Pointcut(value = "@annotation(com.j2ee.spring.spring_aop.example2_annotation.AdressAnnotation)" )
    public void addressPointCuts(){}

    @Before(value= "addressPointCuts() && @annotation(adressAnnotation)",argNames="joinPoint,adressAnnotation")
    public void before(JoinPoint joinPoint,AdressAnnotation adressAnnotation) {
        Object object=joinPoint.getTarget();
        System.out.println("object:"+object);
        System.out.println("示例2切面before增强方法:"+adressAnnotation.province()+" "+adressAnnotation.city()
                +" "+adressAnnotation.isforeign()+" "+ adressAnnotation.countyNum());
        System.out.println("[AddressAspect1] before advise");
    }
}
