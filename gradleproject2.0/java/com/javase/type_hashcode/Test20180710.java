package com.javase.type_hashcode;

import org.junit.Test;

/**
 * Created by zjm on 2018/7/10.
 */
public class Test20180710 {


    /**
     *  字符串转化为hashcode
     */
    @Test
    public void test3_20180821(){
        String str=new String("A");
        int hashcode=str.hashCode();
        System.out.println(hashcode+":"+Integer.toBinaryString(hashcode));
    }

    @Test
    public void  test2_20180716(){
        String a="B";
        int b=a.hashCode()&0x7FFFFFFF;
        System.out.println("aa type_hashcode:"+a.hashCode()+":"+b);
        System.out.println("---:"+"朱".hashCode());
        int i=2147483647;//int类型的最大值
        System.out.println(0x7FFFFFFF);
    }

    @Test
    public void  test1_20180710(){
        String key="fick";
        int h;
        int keyhashcode=(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        System.out.println("keyhashcode:"+keyhashcode);
        System.out.println(100 & keyhashcode);
    }




}
