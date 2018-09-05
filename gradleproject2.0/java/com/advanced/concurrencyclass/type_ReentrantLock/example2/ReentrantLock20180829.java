package com.advanced.concurrencyclass.type_ReentrantLock.example2;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock获取锁，unlock释放锁，await等待，signal唤醒
 */
public class ReentrantLock20180829 {

    private Lock lock = new ReentrantLock();// 非公平锁
    //private Lock lock = new type_ReentrantLock(true);//公平锁
    public static void main(String[] args)  {
        ReentrantLock20180829 test=new ReentrantLock20180829();
        test.test3();
        //test.test4();
    }

    /**
     * 唤醒等待的锁
     */
    public void signal()
    {
        lock.lock();
        condition.signal();//condition对象的signal方法可以唤醒wait线程，必须先执行lock.lock方法获得锁
    }

    /**
     * 获取锁跟解锁
     */
    @Test
    public void test1() {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadName=" + Thread.currentThread().getName()
                    + (" " + (i + 1)));
        }
        lock.unlock();
    }

    private Condition condition=lock.newCondition();

    /**
     *  单个线程的等待唤醒
     */
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

    /**
     * 多线程的唤醒与等待 1
     */
    @Test
    public void test3() {
        final Lock lock = new ReentrantLock();
        final Condition conditionA=lock.newCondition();
        final Condition conditionB=lock.newCondition();

        final NumberWrapper num = new NumberWrapper();
         Thread threadA=new Thread(()->{
            try {
                System.out.println("进入线程A");
                lock.lock();
                while (num.value <= 4) {
                    System.out.println("线程A等待");
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            try {
                lock.lock();
                while (num.value <= 8) {
                    System.out.println("threadA:"+num.value);
                    num.value++;
                }
                System.out.println("唤醒线程B");
                conditionB.signal();
            } finally {
                lock.unlock();
            }
        });
         threadA.setName("线程A");

        Thread threadB=new Thread(()->{
                 lock.lock();
            try {
                System.out.println("进入线程B");
                while (num.value <= 4) {
                    System.out.println("threadB:"+num.value);
                    num.value++;
                }
                System.out.println("唤醒线程A");
                conditionA.signal();
            } finally {
                lock.unlock();
            }

            lock.lock();
            try {
                System.out.println("线程B等待");
                conditionB.await();
                while (num.value <= 12) {
                    System.out.println("threadB:"+num.value);
                    num.value++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        threadB.setName("线程B");
        threadA.start();
        threadB.start();

    }

    /**
     * 多线程的唤醒与等待2
     */
    public void test4(){
        //初始化可重入锁
        final Lock lock = new ReentrantLock();

        //第一个条件当屏幕上输出到3
        final Condition reachThreeCondition = lock.newCondition();
        //第二个条件当屏幕上输出到6
        final Condition reachSixCondition = lock.newCondition();

        //NumberWrapper只是为了封装一个数字，一边可以将数字对象共享，并可以设置为final
        //注意这里不要用Integer, Integer 是不可变对象
        final NumberWrapper num = new NumberWrapper();
        //初始化A线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                //需要先获得锁
                lock.lock();
                try {
                    System.out.println("threadA start write");
                    //A线程先输出前3个数
                    while (num.value <= 3) {
                        System.out.println(num.value);
                        num.value++;
                    }
                    //输出到3时要signal，告诉B线程可以开始了
                    reachThreeCondition.signal();
                } finally {
                    lock.unlock();
                }
                lock.lock();
                try {
                    //等待输出6的条件
                    reachSixCondition.await();
                    System.out.println("threadA start write");
                    //输出剩余数字
                    while (num.value <= 9) {
                        System.out.println(num.value);
                        num.value++;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();

                    while (num.value <= 3) {
                        //等待3输出完毕的信号
                        reachThreeCondition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                try {
                    lock.lock();
                    //已经收到信号，开始输出4，5，6
                    System.out.println("threadB start write");
                    while (num.value <= 6) {
                        System.out.println(num.value);
                        num.value++;
                    }
                    //4，5，6输出完毕，告诉A线程6输出完了
                    reachSixCondition.signal();
                } finally {
                    lock.unlock();
                }
            }

        });
        //启动两个线程
        threadB.start();
        threadA.start();
    }

    class NumberWrapper {
        public int value = 1;
    }

}

