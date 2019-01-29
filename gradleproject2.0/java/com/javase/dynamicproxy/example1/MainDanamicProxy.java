package com.javase.dynamicproxy.example1;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

/**
 * 实现类代理
 */
public class MainDanamicProxy {
    /**
     * 类似静态代理，自定义接口实现类
     */
    @Test
    public void  test(){
        Calculator calculator=new CalculatorImpl();
        LogHandler logHandler=new LogHandler(calculator);
        Calculator proxy= (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(),
                calculator.getClass().getInterfaces(),logHandler);
        int sum=proxy.add(11,22);
        System.out.println("sum:"+sum);
    }

    /**
     * 非静态代理，接口的实现类没有完全一样
     */
    @Test
    public void  test2 (){

        Calculator newObject=new Calculator() {
            @Override
            public int add(int a, int b) {
                return (a+b)*2000;
            }
        };

        Calculator calculator = LogHandler.newMapperProxy(Calculator.class,newObject);
        int sum=calculator.add(11,22);
        System.out.println("sum:"+sum);
    }

    /**
     * 生成JDK的动态代理文件
     */
    @Test
    public void test3(){
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", Calculator.class.getInterfaces());
        String path = "F:/Calculator.class";
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }

}
