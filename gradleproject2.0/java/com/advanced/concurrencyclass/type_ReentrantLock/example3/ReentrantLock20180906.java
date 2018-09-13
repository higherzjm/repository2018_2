package com.advanced.concurrencyclass.type_ReentrantLock.example3;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zjm on 2018/9/6.
 * 测试一个线程未释放锁之前第二个线程能否获取锁
 */
public class ReentrantLock20180906 {
    Lock lock=new ReentrantLock();
    final Condition conditionA=lock.newCondition();
    final Condition conditionB=lock.newCondition();

    public static void main(String[] args){
        ReentrantLock20180906 reentrantLock20180906=new ReentrantLock20180906();
        reentrantLock20180906.test1();
    }

    /**
     * lock.tryLock()):锁被其他线程持有返回false,否则返回true
     * 按正常如果线程1没有释放锁，线程2锁会获取不成功
     */
    public void test1(){
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }

    class Thread1 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread1 tryLock:"+lock.tryLock());
            try {
                lock.lock();
                System.out.println("thread1 获取锁成功");
            }finally {
                lock.unlock();
            }


        }
    }

    class Thread2 implements Runnable{

        @Override
        public void run() {
           /* try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("thread2 tryLock:"+lock.tryLock());
            lock.lock();
            System.out.println("thread2 获取锁成功");

        }
    }
}


