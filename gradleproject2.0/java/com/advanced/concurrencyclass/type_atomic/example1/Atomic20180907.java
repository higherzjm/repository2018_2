package com.advanced.concurrencyclass.type_atomic.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  原子类: 要嘛执行完整，要嘛不执行
 */
public class Atomic20180907 {
 
    public static void main(String[] args){
        final Counter cas=new Counter();
        cas.atomicI.set(999);//初始化值
        List<Thread> ts=new ArrayList(600);
        long start=System.currentTimeMillis();
        for(int j=0;j<100;j++){
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for(Thread t:ts){
            t.start();
        }
 
        for(Thread t:ts){
            try {
                t.join();//Waits for this thread to die. 等待线程执行完毕再往下走
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("非线程安全计数器:"+cas.i);
        System.out.println("使用CAS实现线程安全计数器:"+cas.atomicI.get());
        System.out.println("所花时间:"+(System.currentTimeMillis()-start));
    }
 
}
 
 class Counter {
    public AtomicInteger atomicI=new AtomicInteger(0);

    public int i=0;
    /**
    * 使用CAS实现线程安全计数器,多线程情况下数据也会准
    */
    public void safeCount(){
        for(;;){
         int  currentValue=atomicI.get();
            boolean suc=atomicI.compareAndSet(currentValue,++currentValue);
            if(suc){
                break;
            }
        }

    }
 
    /**
    * 非线程安全计数器,多线程情况下数据不准
    */
    public void count(){
        i++;//费原子性的递增
    }
 
}