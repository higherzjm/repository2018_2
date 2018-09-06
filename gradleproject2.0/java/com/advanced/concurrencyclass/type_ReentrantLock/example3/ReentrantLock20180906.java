package com.advanced.concurrencyclass.type_ReentrantLock.example3;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zjm on 2018/9/6.
 */
public class ReentrantLock20180906 {
    Lock lock=new ReentrantLock();

    /**
     * lock.tryLock()):锁被其他线程持有返回false,否则返回true
     */
    @Test
    public void test1(){
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }

    class Thread1 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread1 tryLock:"+lock.tryLock());
            lock.lock();
        }
    }

    class Thread2 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread2 tryLock:"+lock.tryLock());
            lock.lock();
        }
    }
}


