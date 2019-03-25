package com.j2ee.spring.spring_aop.example4_staticproxy;

import java.util.Date;

/**
 * 日志类代码
 */
public class Logger {
    public static void start(){
        System.out.println(new Date()+ " say hello start...");
    }
    
    public static void end(){
        System.out.println(new Date()+ " say hello end");
    }
}