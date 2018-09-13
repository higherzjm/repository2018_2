package com.advanced.concurrencyclass.type_ReentrantLock.example3;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zjm on 2018/9/6.
 * 测试一个线程未释放锁之前第二个线程能否获取锁
 */
public class ReentrantLock20180906 {
    Lock lock=new ReentrantLock();

    public static void main(String[] args){
        ReentrantLock20180906 reentrantLock20180906=new ReentrantLock20180906();
        reentrantLock20180906.test1();
    }

    /**
     * lock.tryLock()):锁被其他线程持有返回false,否则返回true
     * 按正常如果线程1没有释放锁，线程2锁会获取不成功
     */
    public void test1(){
         Thread thread1=new Thread(new Thread1());
         thread1.setName("thread1");
         thread1.start();

         Thread thread2=new Thread(new Thread2());
         thread2.setName("thread2");
         thread2.start();

         Thread thread3=new Thread(new Thread3());
         thread3.setName("thread3");
         thread3.start();
    }

    class Thread1 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread1 tryLock:"+lock.tryLock());
            lock.lock();
            System.out.println("thread1 获取锁成功");
            lock.unlock();
        }
    }

    class Thread2 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread2 tryLock:"+lock.tryLock());
            lock.lock();
            System.out.println("thread2 获取锁成功");

        }
    }

    class Thread3 implements Runnable{

        @Override
        public void run() {
            System.out.println("thread3 tryLock:"+lock.tryLock());
            lock.lock();
            System.out.println("thread3 获取锁成功");
        }
    }
}


