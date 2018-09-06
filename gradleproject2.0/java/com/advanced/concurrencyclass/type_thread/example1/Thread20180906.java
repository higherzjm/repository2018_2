package com.advanced.concurrencyclass.type_thread.example1;


/**
 * Created by zjm on 2018/9/6.
 */
public class Thread20180906 {
    public static void main(String[] args){
        Thread20180906 thread20180906=new Thread20180906();
        thread20180906.test1();

    }

    /**
     * 一个线程在运行状态中，其中断标志被设置为true,则此后，一旦线程调用了wait、jion、sleep方法中的一种，
     * 立马抛出一个InterruptedException，且中断标志被清除，重新设置为false。
     */
    public void test1(){

        Thread thread1=new Thread(new Thread1());
        Thread thread2=new Thread(new Thread2());
        thread1.setDaemon(false);
        thread1.start();
        thread2.setDaemon(false);
        thread2.start();
        System.out.println(Thread.currentThread().getName());
    }



     class Thread1 implements  Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1");
        }
    }

    class Thread2 implements  Runnable{

        @Override
        public void run() {
            try {
                System.out.println();
                System.out.println("默认中断状态为false:"+Thread.currentThread().isInterrupted());//中断状态
                Thread.currentThread().interrupt();//当前线程中断
                System.out.println("设置状态状态为true:"+Thread.currentThread().isInterrupted());//中断状态
                Thread.sleep(3000);//中断后执行会抛出异常
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("抛出异常后默认设置中断状态为false:"+Thread.currentThread().isInterrupted());//抛出异常后默认设置中断状态为false
            }
            System.out.println("thread2");
        }
    }
}
