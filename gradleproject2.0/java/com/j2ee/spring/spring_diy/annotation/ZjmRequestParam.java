package com.j2ee.spring.spring_diy.annotation;

import java.lang.annotation.*;

/**
 * Created by zjm on 2019/3/19.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZjmRequestParam {
    String value() default "";
}
