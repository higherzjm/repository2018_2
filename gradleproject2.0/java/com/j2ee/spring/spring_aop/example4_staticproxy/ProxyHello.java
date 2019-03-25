package com.j2ee.spring.spring_aop.example4_staticproxy;

/**
 * 代理类代码
 */
public class ProxyHello implements IHello{
    private IHello hello;    
    public ProxyHello(IHello hello) {
        super();
        this.hello = hello;
    }
    @Override
    public void sayHello(String str) {
        Logger.start();//添加特定的方法
        hello.sayHello(str);
        Logger.end();
    }

}