package com.javase.thread.test2019_01;

/**
 * Created by zjm on 2018/12/31.
 */
public class Demo20181231 {
    public  static int count=0;
    public volatile static int count2=0;
    public   static  void add(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public   static  void add2(){
        count2++;
    }

    public  static void main(String[] args){
        for (int i=0;i<10000;i++){
            new Thread(Demo20181231::add
            ).start();
            new Thread(Demo20181231::add2
            ).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count:"+count+" count2:"+count2);
    }
}
