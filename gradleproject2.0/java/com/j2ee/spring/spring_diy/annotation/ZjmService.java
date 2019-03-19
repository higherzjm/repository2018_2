package com.j2ee.spring.spring_diy.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Created by zjm on 2019/3/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZjmService {
    String value() default "";
}
