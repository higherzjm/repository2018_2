package com.javase.type_numberSymbol;

import org.junit.Test;

/**
 * Created by zjm on 2018/8/21.
 * 各种数据类型与符号
 */
public class Test_20180821 {


    @Test
    public void test2_20180821(){
        Integer a=100;
        String binaryNum=Integer.toBinaryString(a);//十进制转化为二进制
        System.out.println("toBinaryString:"+binaryNum);
        int binaryNumTo=Integer.valueOf(binaryNum,2);
        System.out.println("binaryNumTo:"+binaryNumTo);//二进制转化为十进制
        String hexNum=Integer.toHexString(a); //十进制转化为十六进制
        System.out.println("toHexString:"+hexNum);
        int hexNumTo=Integer.valueOf(hexNum,16);
        System.out.println("hexNumTo:"+hexNumTo);//十六进制转化为十进制

        int b=0X64;//十六进制表示方法
        b=0XFF;
        System.out.println("0X64十六进制:"+b);


    }

    /**
     * 符合>>、<<、&、|、^ 的运用
     */
    @Test
    public void test1_20180821(){
        System.out.println(9>>2);//右移2为，相当于除以4
        System.out.println(11<<2);//左移2为，相当于乘以4
        //两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0
        System.out.println(Integer.toBinaryString(18)+"&"+Integer.toBinaryString(19)+":"+(18&19));//位与运算符（&）

        //两个数都转为二进制，然后从高位开始比较，两个数只要有一个为1则为1，否则就为0
        System.out.println(Integer.toBinaryString(18)+"|"+Integer.toBinaryString(19)+":"+(18|19));//位或运算符（|）

        //两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1
        System.out.println(Integer.toBinaryString(18)+"^"+Integer.toBinaryString(19)+":"+(18^19));//位异或运算（^）
    }
}
