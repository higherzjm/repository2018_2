package com.javase.basetest.str;

import org.junit.Test;

/**
 * Created by zjm on 2019/3/26.
 */
public class Test20190326 {
    /**
     * 循环读取字符串中的每个字母
     */
    @Test
    public void  test1(){
       String  str="'>< =&|%*";
        for(int i=0;i<str.length();i++){
            String subStr = str.substring(i, i+1);
            System.out.println("subStr:"+subStr);
        }
    }

    /**
     * 按指定符号切分字符串
     */
    @Test
    public void  test2(){
        String  str="CardholderEmail/getEmailsByTxId;CardholderEmail/getEmailContent";
        for (String splitStr:str.split(";")){
            System.out.println("splitStr:"+splitStr);
        }
    }
}
