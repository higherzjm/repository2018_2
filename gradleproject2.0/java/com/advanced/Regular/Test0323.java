package com.advanced.Regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjm on 2019/3/23.
 */
public class Test0323 {
    @Test
    public void  test1(){
        String content = "I am noob from runoob.com.";
        String pattern = "/^(\\d{2,},)*\\d+$/";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }

    @Test
    public void  test2(){

        String str = "abc";

        // 根据以前的方法进行判断
        // System.out.println(str.equals("abc"));

        /**
         * 用正则表达式来判断
         * 1.compile(String regex)    将给定的正则表达式编译到模式中。
         * 2.matcher(CharSequence input)    创建匹配给定输入与此模式的匹配器。
         * 3.matches()    尝试将整个区域与模式匹配。
         */
        // 首先要编译正则规则形式
        Pattern p = Pattern.compile("abc");
        // 将正则进行匹配
        Matcher m = p.matcher(str);
        // 进行判断
        boolean b = m.matches();
        System.out.println(b);
    }
    @Test
    public void test3(){
        String str = "agx";
        // 指定判断规则(中括号内字符顺序随便)
        String regex="[abc][edgf][xzy]";
        Pattern p = Pattern.compile(regex);
        // 进行规则匹配
        Matcher m = p.matcher(str);
        // 进行判断
        boolean b = m.matches();
        System.out.println(b);
    }

    //字符串中只能包含数字或字母或逗号
    @Test
    public void test4(){
        String str = "httpswwwcnblogscomxxt19970908p5587748html";
        // 指定判断规则(中括号内字符顺序随便)
        String  regx="^[0-9a-zA-Z,]+$";
        Pattern p = Pattern.compile(regx);
        // 进行规则匹配
        Matcher m = p.matcher(str);
        // 进行判断
        boolean b = m.matches();
        System.out.println(b);
    }
}
