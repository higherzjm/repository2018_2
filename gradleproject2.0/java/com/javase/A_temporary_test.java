package com.javase;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zjm on 2017/11/20.
 *
 */
public class A_temporary_test {

    @Test
    public void test19(){
        Enumeration<String>[] enumerations = (Enumeration<String>[]) new Enumeration<?>[2];
    }

    /**
     *集合数组
     */
    @Test
    public void test18(){
        List<String> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        String strs2=new String();
        String[] strs=new String[100];
        Map<String,Object>[]  maps=(HashMap<String,Object>[])new HashMap<?,?>[2];
        List<String>[] lists=(ArrayList<String>[])new ArrayList<?>[2];
        Enumeration<URL>[] tmps = (Enumeration<URL>[]) new Enumeration<?>[2];
    }

    /**
     * Package类
     */
    @Test
    public void test17(){
        System.out.println(this.getClass().getPackage().getName());
        Package p=this.getClass().getPackage();
    }
    /**
     * 调用过时方法
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test16(){
       Date date=new Date();
       date.getDate();
    }

    /**
     *char数组和String的双向转换
     *  @SuppressWarnings( )注解
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test15(){
        String str="1hangsan";
        char[] chars = str.toCharArray();
        chars[0] += 1;
        System.out.println(String.valueOf(chars));
    }

    /**
     * jsonobject 双向转换
     */
    @Test
    public void test14(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.accumulate("name","张三");
        jsonObject.accumulate("age",18);
        String str=jsonObject.toString();
        System.out.println("str:"+str);
        JSONObject jsonObject1=JSONObject.fromObject(str);
        System.out.println("str2:"+jsonObject1.toString());
    }

    /**
     * 死循环的另外一种表示方式
     */
    @Test
    public  void test13(){
        //死循环
        for (;;){
            System.out.println(1000);
        }
    }

    /**
     * char 转化为int
     */
    @Test
    public void test12(){
        char values[]=new char[2];
        values[0]='A';values[1]='中';
        for (char value:values ) {
            int h = value;
            System.out.println("h:"+h);
        }
    }

    /**
     * 进制转换
     */
    @Test
    public void test11(){
        String binaryNum=Integer.toBinaryString(37);//十进制转化为二进制
        System.out.println(binaryNum);
    }

    /**
     * 日期格式的双向格式化
     */
    @Test
    public void test10(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=fmt.parse("2018-10-10");
            System.out.println("date:"+date.getTime());
            String dateStr=fmt.format(date);
            System.out.println("dateStr:"+dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * list集合的独立修改实现
     */
    @Test
    public  void test9(){
        List<Student> students=new ArrayList<>();
        for (int i=0;i<10;i++){
            students.add(new Student(String.valueOf(i),"张三"+i));
        }
        System.out.println("students:"+students);
        students.get(1).changeName();
        System.out.println("-------------------------------------");
        System.out.println("students2:"+students);
    }
    /**
     * 16进制
     */
    @Test
    public void test8(){
        int a=0xaa;
        System.out.println(a);
    }

    /**
     *  二进制左移右移
     */
    @Test
    public void test7(){
        System.out.println(9>>2);//右移2为，相当于除以4
        System.out.println(11<<2);//左移2为，相当于乘以4

    }

    /**
     * 常量池的应用
     *
     * String的intern()方法就是扩充常量池的一个方法；当一个String实例str调用intern()方法时，
     * Java查找常量池中是否有相同Unicode的字符串常量，如果有，则返回其的引用，如果没有，
     * 则在常量池中增加一个Unicode等于str的字符串并返回它的引用
     */
    @Test
    public void test6(){
        String a="aabb";//常量池变量，编译期确认
        String b=new String("aabb");//非常量池变量，运行期确认，它们有自己的地址空间
        System.out.println(a==b);//false
        System.out.println(a.equals(b));//true
        String c=b.intern();//常量池变量转移
        System.out.println(a==c);//true

    }

    /**
     *a--与--a的区别
     */
    @Test
    public void test5(){
        int a=10;
        while (a-->0){//比较完再减去
            System.out.println(a);
        }

        a=10;
        System.out.println("------------------");
        while (--a>0){//先减去再比较
            System.out.println(a);
        }
    }

    /**
     * switch使用
     */
    @Test
    public void test4(){
        for (int i=0;i<10;i++){
            switch (i){
                case 0: System.out.println("-------------0"); break;
                case 1:
                case 2:
                case 9:
                default:
                    System.out.println("switch case:"+i);
            }
        }
    }

    /**
     * 子数组截取
     */
    @Test
    public  void test3(){
        List<Integer>  list=new ArrayList<>();
        for (int i=0;i<100;i++){
            list.add(i);
        }
        System.out.println(list.subList(0,20));

    }

    /**
     * 写xlsx excel
     */
    @Test
    public  void  test2(){
        try  {
            WritableWorkbook book = Workbook.createWorkbook( new File( "E:\\工作记录\\1031\\test.xlsx " ));

            for (int page=0;page<5;page++){
                WritableSheet sheet = book.createSheet( " 第"+page+"页 " , 0 );
                System.out.println("第 "+page+" 页");
                for (int i=0;i<20;i++){
                    for (int j=0;j<55;j++){
                        Label label =  new Label( j , i , " test " );//列,行,值
                        sheet.addCell(label);
                    /*jxl.write.Number number =  new jxl.write.Number( 1 , i , 555.12541 );
                    sheet.addCell(number);*/
                    }
                }
            }
            System.out.println("更新结束");
            book.write();
            book.close();

        }  catch (Exception e) {
            System.out.println(e);
        }
    }
    @Test
    public  void test1(){
        System.out.println("123");
    }


}

class  Student{
    public String id;
    public String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(){
        this.name="change:"+this.name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

