package com.advanced.concurrencyclass.type_Semaphore.example1;

import java.util.concurrent.Semaphore;

public class Test {
    public static void main(String[] args) {
        int N = 7;            //工人数
        Semaphore semaphore = new Semaphore(5); //机器数目
        for(int i=0;i<N;i++)
            new Worker(i,semaphore).start();
    }
 
    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }
 
        @Override
        public void run() {
            try {
                System.out.println("可用的信号量:"+semaphore.availablePermits()+";正在等待信号的量:"+semaphore.getQueueLength());//可用的信号量;获取正在等待信号的量
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                Thread.sleep(5000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();           
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}