package com.advanced.concurrencyclass.type_CountDownLatch.example2;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
 * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
 */
public class Test20180829 {
    @Test
    public  void test1() {
        int threadNum=3;
        final CountDownLatch latch = new CountDownLatch(threadNum);

        new Thread(() -> {
            try {
                System.out.println("子线程1 " + Thread.currentThread().getName() + "正在执行");
                Thread.sleep(30000);
                System.out.println("子线程1 " + Thread.currentThread().getName() + "执行完毕");
                latch.countDown();//执行一次数量减去1

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("子线程2 " + Thread.currentThread().getName() + "正在执行");
                Thread.sleep(20000);
                System.out.println("子线程2 " + Thread.currentThread().getName() + "执行完毕");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        try {
            latch.countDown();//执行一次数量减去1
            System.out.println("等待2个子线程执行完毕...");
            latch.await();//调用 threadNum 次 latch.countDown()才会执行后面的代码
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}