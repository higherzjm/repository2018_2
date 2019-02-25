package com.j2ee.log;

import org.junit.Test;


/**
 * Created by zjm on 2019/2/25.
 *
 * 常用的日志类型
 */
public class MainClass_alltypelog {

    /**
     * dk自带(JUL)，红色字体
     */
    @Test
    public  void test1jdkLog(){
        java.util.logging.Logger log = java.util.logging.Logger.getLogger(String.valueOf(MainClass_alltypelog.class));
        log.info("jdkLog_abc123张三");
        test2_log4j();
        test3_commonsLog();
        test4_slf4jLog();
    }

    /**
     * 白色字体，跟JCL格式一样
     */
    @Test
    public  void test2_log4j(){
        org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(String.valueOf(MainClass_alltypelog.class));
        log.info("log4j_abc123张三");
    }

    /**
     * Jakarta  Commons-logging（JCL）是apache最早提供的日志的门面接口。提供简单的日志实现以及日志解耦功能。
     * 白色字体，跟log4j格式一样
     */
    @Test
    public  void test3_commonsLog(){
        org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MainClass_alltypelog.class);
        log.info("JCL_abc123张三");
    }

    /**
     *简单日志门面(Simple Logging Facade for Java)
     * 白色字体,，跟上面两种格式一样
     */
    @Test
    public  void test4_slf4jLog(){
        org.slf4j.Logger  log = org.slf4j.LoggerFactory.getLogger(MainClass_alltypelog.class);
        log.info("slf4jLog_abc123张三");
    }
}
