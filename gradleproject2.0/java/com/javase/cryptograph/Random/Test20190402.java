package com.javase.cryptograph.Random;

import org.junit.Test;

import java.util.Random;

/**
 * Created by zjm on 2019/4/2.
 */
public class Test20190402 {

    /**
     * 生成32位随机数的例子
     */
    @Test
    public  void  test1(){
        Random rand = new Random();
        StringBuffer sb=new StringBuffer();
        for (int i=1;i<=32;i++){
            int randNum = rand.nextInt(9)+1;
            String num=randNum+"";
            sb=sb.append(num);
        }
        String random=String.valueOf(sb);
        System.out.println("random:"+random);

    }
}
