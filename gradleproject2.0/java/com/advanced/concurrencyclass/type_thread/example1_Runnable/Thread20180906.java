package com.advanced.concurrencyclass.type_thread.example1_Runnable;


import java.util.concurrent.locks.LockSupport;

/**
 * Created by zjm on 2018/9/6.
 */
public class Thread20180906 {
    public static void main(String[] args){
        Thread20180906 thread20180906=new Thread20180906();
       // thread20180906.test_interrupt();//测试线程中断操作(此中断只是一个状态，线程不会停止)
        //thread20180906.test_join();//测试等待线程执行完毕程序才执行后面的部分
        //thread20180906.test_currentThread(1);//测试当前线程
        thread20180906.test_LockSupport();//测试线程阻塞和唤醒，真正的阻塞和唤醒(代码会停止继续执行)

    }

    /**
     * 一个线程在运行状态中，其中断标志被设置为true,则此后，一旦线程调用了wait、jion、sleep方法中的一种，
     * 立马抛出一个InterruptedException，且中断标志被清除，重新设置为false。
     * 一个线程中断，捕捉了异常不影响其他线程的继续执行
     */
    public void test_interrupt(){
        Thread.currentThread().interrupt();//这边中断不影响后面程序的运行，只是一个状态而已
        Thread thread1=new Thread(new Thread1());
        Thread thread2=new Thread(new Thread2());
        thread1.setDaemon(false);//false就是非守护线程->线程都执行完才结束整个方法，true为守护线程->方法结束所有就结束
        thread1.start();
        thread2.setDaemon(false);
        thread2.start();

    }

    /**
     * join()：Waits for this thread to die. 等待线程执行完毕程序才执行后面的部分
     */
   public void test_join(){
         Thread thread=new Thread(new Thread1());
         thread.start();
           try {
               thread.join();//需要放到start后面才有效果
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        System.out.println("11111111");
   }

    /**
     * 当前线程
     *  普通方法的currentThreadName 一般为main
     *  从线程内部调用的方法的currentThreadNam都为线程的名称
     */
   public void test_currentThread(int type){
       if (type==1) {
           Thread thread = new Thread(new Thread3());
           thread.setName("我的currentThread线程");
           thread.start();
           System.out.println("test_currentThread:" + Thread.currentThread().getName());
       }else {
           System.out.println("test_currentThread2:" + Thread.currentThread().getName());
       }
   }

   public void test_LockSupport(){
         Thread thread=new Thread(new Thread_park());
         thread.start();//阻塞当前线程
         Thread thread2=new Thread(new Thread_unpark(thread));
         thread2.start();//唤醒被阻塞的线程

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

    class Thread3 implements Runnable{

        @Override
        public void run() {
            currentThread_outmethod();
            System.out.println("currentThread_innermethod:"+Thread.currentThread().getName());

        }
    }

    public void currentThread_outmethod(){
        System.out.println("currentThread_outmethod:"+Thread.currentThread().getName());
        test_currentThread(2);
    }


    class Thread_park implements  Runnable{
        @Override
        public void run() {
            System.out.println("Thread_park开始");
            LockSupport.park(this);//线程阻塞了
            System.out.println("Thread_park结束");
        }
    }

    class Thread_unpark implements  Runnable{
        private Thread thread;

        public Thread_unpark(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("Thread_unpark开始");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(thread);//线程唤醒了
            System.out.println("Thread_unpark结束");
        }
    }

}
