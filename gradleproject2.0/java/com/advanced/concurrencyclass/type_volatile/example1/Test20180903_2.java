package com.advanced.concurrencyclass.type_volatile.example1;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test20180903_2 {
    public volatile int inc = 0;
     
    public void increase() {
        inc++;
    }
     

   //自增操作不是原子性操作，而且volatile也无法保证对变量的任何操作都是原子性的
    /**
     * 假如某个时刻变量inc的值为10，
       线程1对变量进行自增操作，线程1先读取了变量inc的原始值，然后线程1被阻塞了；
      然后线程2对变量进行自增操作，线程2也去读取变量inc的原始值，由于线程1只是对变量inc进行读取操作，
     而没有对变量进行修改操作，所以不会导致线程2的工作内存中缓存变量inc的缓存行无效，所以线程2会直接去主存读取inc的值，
     发现inc的值时10，然后进行加1操作，并把11写入工作内存，最后写入主存。

     然后线程1接着进行加1操作，由于已经读取了inc的值，注意此时在线程1的工作内存中inc的值仍然为10，
     所以线程1对inc进行加1操作后inc的值为11，然后将11写入工作内存，最后写入主存。
     */

    @Test
    public  void test1(){//每次结果不一样，不一定为100000
        final Test20180903_2 test = new Test20180903_2();
        for(int i=0;i<100;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++)
                    test.increase();
            }).start();
        }
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
        {
            Thread.yield();
        }
        System.out.println(test.inc);
    }

    /**
     * synchronized 保证操作的原子性
     */
    public synchronized void increase2() {
        inc++;
    }
    @Test
    public  void test2(){//结果都会为100000
        final Test20180903_2 test = new Test20180903_2();
        for(int i=0;i<100;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase2();
                };
            }.start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }

    /**
     * Lock 保证操作的原子性
     */
    Lock lock = new ReentrantLock();
    public  void increase3() {
        lock.lock();
        try {
            inc++;
        } finally{
            lock.unlock();
        }
    }
    @Test
    public void  test3(){//结果都会为100000
        final Test20180903_2 test = new Test20180903_2();
        for(int i=0;i<100;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase3();
                };
            }.start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }


    /**
     * AtomicInteger 保证操作的原子性
     * atomic是利用CAS来实现原子性操作的（Compare And Swap），
     * CAS实际上是利用处理器提供的CMPXCHG指令实现的，而处理器执行CMPXCHG指令是一个原子性操作。
     */
    public AtomicInteger  atomicInteger = new AtomicInteger();
    public  void increase4() {
        atomicInteger.getAndIncrement();
    }
    @Test
    public void test4(){//结果都会为100000
        final Test20180903_2 test = new Test20180903_2();
        for(int i=0;i<100;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase4();
                };
            }.start();
        }

        while(Thread.activeCount()>1) { //activeCount存在活跃的线程数，保证前面的线程都执行完
            Thread.yield();// 为了合理分配cpu资源 It is rarely appropriate to use this method. It may be useful for debugging or testing purposes,
        }
        System.out.println(test.atomicInteger);
    }

}