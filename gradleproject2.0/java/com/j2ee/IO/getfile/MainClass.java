package com.j2ee.IO.getfile;

import com.javase.A_temporary_test;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by zjm on 2018/11/23.
 */
public class MainClass {


    /**
     * 读取项目WEB-INF目录下的text文件
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
     String path=this.getClass().getResource("").getPath();
     System.out.println("path:"+path);
    }
    /**
     * 读取项目跟目录下的text文件
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
        InputStream fis=null;
        //fis = A_temporary_test.class.getClassLoader().getResourceAsStream("redis.properties");// 可以读取
        //方法2
        fis = ClassLoader.getSystemResourceAsStream("mytxt.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "gbk"));
        String s = "";
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        fis.close();// 关闭流
    }
    /**
     * 读取项目跟目录下的Properties文件
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        Properties prop = new Properties();// 属性集合对象
        InputStream fis=null;
        fis = A_temporary_test.class.getClassLoader().getResourceAsStream("redis.properties");// 可以读取

        //方法2
        //fis = ClassLoader.getSystemResourceAsStream("redis.properties");
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
        String redisPort=prop.getProperty("redis.port");
        System.out.println("读取txnLogId属性的值："+redisPort);

    }
}
