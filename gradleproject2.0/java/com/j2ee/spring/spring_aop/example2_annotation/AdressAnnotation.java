package com.j2ee.spring.spring_aop.example2_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zjm on 2019/1/6.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdressAnnotation {
    String province();
    String city();
    boolean isforeign();
    int countyNum();
}
