package com.j2ee.IO.getfile;

import com.javase.A_temporary_test;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * Created by zjm on 2018/11/23.
 */
public class MainClass {

     @Test
     public void  test4() throws IOException {
         Properties props = new Properties();
         props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties"));//从自定义配置文件获取相关参数
        String port = props.getProperty("redis.port");
        String host = props.getProperty("redis.host");
        String timeout = props.getProperty("redis.timeout");
         System.out.println("port:"+port);
         System.out.println("host:"+host);
         System.out.println("timeout:"+timeout);
     }
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
        //fis = A_temporary_test.class.getClassLoader().getResourceAsStream("mytxt.txt");// 可以读取
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
