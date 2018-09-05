package com.advanced.concurrencyclass.type_volatile.example1;

import org.junit.Test;

/**
 * Created by zjm on 2018/9/3.
 * 一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
 1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，
     这新值对其他线程来说是立即可见的。
 2）禁止进行指令重排序，volatile能在一定程度上保证有序性

 保证一个变量在修改volatile变量时，会让缓存行无效

 本代码无法体现
 */
public class Test20180903 {
    public static volatile String v1="张三";
    public static String v2="张三";

    //伪代码
    /**
     * 这段代码是很典型的一段代码，很多人在中断线程时可能都会采用这种标记办法。
     * 但是事实上，这段代码会完全运行正确么？即一定会将线程中断么？不一定，
     * 也许在大多数时候，这个代码能够把线程中断，但是也有可能会导致无法中断线程
     * （虽然这个可能性很小，但是只要一旦发生这种情况就会造成死循环了）。

     下面解释一下这段代码为何有可能导致无法中断线程。在前面已经解释过，
     每个线程在运行过程中都有自己的工作内存，那么线程1在运行的时候，
     会将stop变量的值拷贝一份放在自己的工作内存当中。

     那么当线程2更改了stop变量的值之后，但是还没来得及写入主存当中，
     线程2转去做其他事情了，那么线程1由于不知道线程2对stop变量的更改，
     因此还会一直循环下去。
     */
    public void test2(){
        //线程1
        boolean stop = false;
        while(!stop){
            //doSomething();
        }

        //线程2
        stop = true;
    }

    @Test
    public void test1(){
      new Thread(new Thread2()).start();
      new Thread(new Thread1()).start();
    }


}
class Thread1 implements  Runnable{

    @Override
    public void run() {
       Test20180903.v1="李四";
       while (true){
           System.out.print(123);
       }

    }
}

class Thread2 implements  Runnable{

    @Override
    public void run() {
       while (Test20180903.v1.equals("张三")){
           System.out.println(Test20180903.v1);
       }
    }
}


