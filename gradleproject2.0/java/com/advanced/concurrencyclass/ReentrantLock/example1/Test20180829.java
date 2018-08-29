package com.advanced.concurrencyclass.ReentrantLock.example1;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test20180829 {

    private Lock lock = new ReentrantLock();
    private Condition condition=lock.newCondition();

    /**
     * 唤醒等待的锁
     */
    public void signal()
    {
        lock.lock();
        condition.signal();//condition对象的signal方法可以唤醒wait线程，必须先执行lock.lock方法获得锁
    }

    @Test
    public void test1() {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadName=" + Thread.currentThread().getName()
                    + (" " + (i + 1)));
        }
        lock.unlock();
    }


    @Test
    public void test2() {
        try {
            new Thread(()->{
                lock.lock();
                try {
                    condition.await();//通过创建Condition对象来使线程wait，必须先执行lock.lock方法获得锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadName=" + Thread.currentThread().getName());
            }).start();
            Thread.sleep(2000);
            signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    @Test
    public void test3() {
        try {
            new Thread(()->{
                lock.lock();
                try {
                    condition.await();//通过创建Condition对象来使线程wait，必须先执行lock.lock方法获得锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程 1 ");
            }).start();

            new Thread(()->{
                lock.lock();
                System.out.println("线程 2 ");
                signal();
                lock.unlock();
            }).start();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //lock.unlock();
        }

    }

}

