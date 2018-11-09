package com.j2ee.spring.spring_quart;

import org.springframework.stereotype.Component;

/**
 * Created by zjm on 2018/11/8.
 */
@Component("srpingquartbean")
public class springQuartrBean {

    public springQuartrBean() {
        System.out.println("实例化srpingquartbean");
    }

    public void srpingquartMethod(){
        System.out.println("springquart方法");
    }

}
