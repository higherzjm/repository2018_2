package com.javase.designpatterns.proxypattern;

/**
 * StudentsProxy类，这个类也实现了Person接口，但是还另外持有一个学生类对象，由于实现了Peson接口，
 * 同时持有一个学生对象，那么他可以代理学生类对象执行上交班费（执行giveMoney()方法）行为。
 * 学生代理类，也实现了Person接口，保存一个学生实体，这样既可以代理学生产生行为
 * @author Gonjan
 *
 */
public class StudentsProxy implements Person{
    //被代理的学生
    Student stu;
    
    public StudentsProxy(Person stu) {
        // 只代理学生对象
        if(stu.getClass() == Student.class) {
            this.stu = (Student)stu;
        }
    }
    
    //代理上交班费，调用被代理学生的上交班费行为
    public void giveMoney() {
        System.out.println("张三最近学习有进步！");
        stu.giveMoney();
    }
}