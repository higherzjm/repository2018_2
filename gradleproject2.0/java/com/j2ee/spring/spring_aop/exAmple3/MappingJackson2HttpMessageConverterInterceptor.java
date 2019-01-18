package com.j2ee.spring.spring_aop.exAmple3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component("mappingJackson2HttpMessageConverterInterceptor")
public class MappingJackson2HttpMessageConverterInterceptor {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MappingJackson2HttpMessageConverterInterceptor.class);


    @Pointcut("execution(public * com.fasterxml.jackson.databind.ObjectMapper.writeValue(..))")
    public void writeValuePointcut() {
    }

    @Before(value = "com.j2ee.spring.spring_aop.example1_execution.PointCuts.aopDemo()")
    public void interceptorPermission(JoinPoint joinPoint) throws Throwable {
        logger.info("123");
    }
}
