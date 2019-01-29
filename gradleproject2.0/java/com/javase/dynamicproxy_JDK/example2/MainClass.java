package com.javase.dynamicproxy_JDK.example2;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Modifier;

/**
 * 接口代理
 */
public class MainClass {

    @Test
    public  void test1(){
        IHello hello = FacadeProxy.newMapperProxy(IHello.class);
        System.out.println("返回值:"+hello.say("hello world"));
    }

    /**
     * 判断是否为公共接口
     */
    @Test
    public  void test2(){
        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(IHello.class.getName(), false, IHello.class.getClassLoader());
        } catch (ClassNotFoundException e) {
        }
        System.out.println("interfaceClass name:"+interfaceClass.getName());
        System.out.println("interfaceClass.isInterface():"+interfaceClass.isInterface());

        int flags = IHello.class.getModifiers();
        System.out.println("flags:"+flags+";ispublic:"+ Modifier.isPublic(flags));
    }


    /**
     * 生成JDK的动态代理文件
     */
    @Test
    public void test3(){
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", IHello.class.getInterfaces());
        String path = "F:/IHello.class";
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类IHelloclass文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }

}  