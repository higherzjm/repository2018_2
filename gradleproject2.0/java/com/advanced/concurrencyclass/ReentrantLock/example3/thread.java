package com.advanced.concurrencyclass.ReentrantLock.example3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Printer3 {
    private ReentrantLock r = new ReentrantLock();
    private Condition c1 = r.newCondition();
    private Condition c2 = r.newCondition();
    private Condition c3 = r.newCondition();
    public static int i = 1;
    public int flag = 1;

    public void print1() throws InterruptedException {
        r.lock();
        if (flag != 1) {
            c1.await();
        }
        if(i==1001) {
            c2.signal();
            c3.signal();
            r.unlock();
            return;
        }
        System.out.println("线程A：" + i++);
        flag = 2;

        c2.signal();
        r.unlock(); // 释放锁
    }

    public void print2() throws InterruptedException {
        r.lock();
        if (flag != 2) {
            c2.await();
        }
        if(i==1001) {
            c1.signal();
            c3.signal();
            r.unlock();
            return;
        }
        System.out.println("线程B：" + i++);
        flag = 3;
        c3.signal();
        r.unlock();
    }

    public void print3() throws InterruptedException {
        r.lock();
        if (flag != 3) {
            c3.await();
        }
        if(i==1001) {   
            c1.signal();
            c2.signal();
            r.unlock();
            return;
        }
        System.out.println("线程C：" + i++);
        flag = 1;
        c1.signal();
        r.unlock();
    }
}

public class thread {

    public static void main(String[] args) {
        final Printer3 p = new Printer3();
        long startTime = System.currentTimeMillis();
        new Thread() {
            public void run() {
                while (true) {
                    try {       
                        if (p.i == 1001) {
                            System.out.println("线程A结束");
                            break;
                        }
                        p.print1();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                while (true) {
                    try {               
                        if (p.i == 1001) {
                            System.out.println("线程B结束");

                            break;
                        }
                        p.print2();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                long  startTime   = System.currentTimeMillis(); //程序结束记录时间
                while (true) {
                    try {
                        if (p.i == 1001) {
                            System.out.println("线程C结束");
                            break;
                        }
                        p.print3();


                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
                long  endTime = System.currentTimeMillis(); //程序结束记录时间
                long TotalTime = endTime - startTime; 
                System.out.print("耗时："+TotalTime);
            }
        }.start();


    }

}