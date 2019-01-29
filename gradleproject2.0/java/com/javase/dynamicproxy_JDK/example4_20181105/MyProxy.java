package com.javase.dynamicproxy_JDK.example4_20181105;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zjm on 2018/11/5.
 */
public class MyProxy {
    @SuppressWarnings("unchecked")
    public static  <T> T myProxyMethod(Class<?> interfaceClass){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    System.out.println("method toGenericString:"+method.toGenericString());
                    System.out.println("method name:"+method.getName());
                    System.out.println("method args:"+(String)args[0]);
                    System.out.println("接口方法调用结束");
                    return  args[0];
                }
        );
    }
    @SuppressWarnings("unchecked")
    public static  <T> T myProxyMethod2(Class<?> interfaceClass){
        return  (T)Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},new InvocationHandler(){
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("调用代理对象invoke方法");
                        return new Dog();
                    }
                }
        );
    }
}
