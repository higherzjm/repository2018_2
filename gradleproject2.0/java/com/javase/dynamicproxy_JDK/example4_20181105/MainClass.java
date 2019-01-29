package com.javase.dynamicproxy_JDK.example4_20181105;

import org.junit.Test;

/**
 * 接口代理
 */
public class MainClass {

    @Test
    public  void test1(){
        IHello hello = MyProxy.myProxyMethod(IHello.class);
        System.out.println("返回结果："+hello.say("hello world"));
    }

    /**
     * 代理方法实例化对象
     */
    @Test
    public  void test2(){
        IHello hello = MyProxy.myProxyMethod2(IHello.class);
        Animal animal=(Animal)hello.initializeClass();
        System.out.println("返回结果："+animal.getName());
    }

}  