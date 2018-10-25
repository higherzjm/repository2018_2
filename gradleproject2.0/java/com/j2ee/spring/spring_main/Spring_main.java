package com.j2ee.spring.spring_main;

import com.j2ee.spring.spring_configuration.OrderService;
import com.j2ee.spring.spring_main.services.InitializingService;
import com.j2ee.spring.spring_main.services.MainService_Annotation;
import com.j2ee.spring.spring_main.services.Mainervice_Declarative;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 使用mainh函数初始化spring配置文件
 */
public class Spring_main {
    public static  void main(String[] args){

    }

    /**
     * 调用初始化的实例测试
     */
    @Test
    public void test1(){
        BeanFactory factory= new FileSystemXmlApplicationContext("classpath:spring_main.xml");
        Mainervice_Declarative mainervice_declarative=(Mainervice_Declarative)factory.getBean("MainerviceDeclarative_bean");
        System.out.println(mainervice_declarative.getName());
        mainervice_declarative.myAction();

        //无法调用注解的实例
        MainService_Annotation mainService_annotation=(MainService_Annotation)factory.getBean("mainserviceannotation");
        mainService_annotation.myAction();
    }

    /**
     * InitializingBean----afterPropertiesSet测试：可以调用注解的bean
     */
    @Test
    public void test2(){
        BeanFactory factory= new FileSystemXmlApplicationContext("classpath:spring_main.xml");
        InitializingService initializingService=(InitializingService)factory.getBean("initializingService");
        initializingService.say();

        MainService_Annotation mainService_annotation=(MainService_Annotation)factory.getBean("mainserviceannotation");
        mainService_annotation.myAction();
    }

    /**
     * 用ClassPathXmlApplicationContext方式初始化声明式bean
     */
    @Test
    public void test3(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring_main.xml");
        Mainervice_Declarative mainervice_declarative=(Mainervice_Declarative)ctx.getBean("MainerviceDeclarative_bean");
        System.out.println("读取name的值:"+mainervice_declarative.getName());
        mainervice_declarative.myAction();

        OrderService orderService=(OrderService)ctx.getBean("orderService");
        System.out.println(orderService.getName("调用orderService类的getName函数"));
    }
}
