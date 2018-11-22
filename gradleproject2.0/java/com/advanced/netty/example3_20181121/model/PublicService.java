package com.advanced.netty.example3_20181121.model;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dengbin on 2016/7/7.
 * 用于标记对外暴露的接口
 * 不需要验证session
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component // 表明可被 Spring 扫描
public @interface PublicService {

    Class<?> value();
}
