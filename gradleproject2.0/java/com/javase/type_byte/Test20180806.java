package com.javase.type_byte;

import org.junit.Test;

/**
 * Created by zjm on 2018/8/6.
 */
public class Test20180806{


    /**
     * char 转化为int
     */
    @Test
    public void  test3_20180821(){
        char values[]=new char[2];
        values[0]='A';values[1]='中';
        for (char value:values ) {
            int h = value;
            System.out.println(h);
        }
    }

    /**
     * 字符串读取bytes
     */
    @Test
    public void test2_20180806(){
        String str="abc123";
        str="张三";
        str=new String("A");
        byte[] bytes=str.getBytes();
        str=new String(bytes);
        System.out.println("str:"+str);
        for (byte b:bytes){
            System.out.println(b+"&"+0xff+"->"+Integer.toBinaryString(b)+"&"+Integer.toBinaryString(0xff)+":"+(0xff &b));
        }

    }

    /**
     * char 与 byte 互相转换
     */
    @Test
    public void test1_20180806(){
        char cr='a';
        byte be=(byte) cr;
        System.out.println(cr+":"+be);

        byte be2=98;
        char cr2= (char) be2;
        System.out.println(be2+":"+cr2);

    }
}
