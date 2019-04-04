package com.advanced.Injection_example;

/**
 * 防注入的例子说明
 */
public class Test_20190327 {
    public static void main(String[] args){
     test1("1 or 1=1");
    }

    /**
     * 会读取所以的情况，读取所有，删除也就所有了
     *
     * @param param
     */
    public static void test1(String param){
        String sql="SELECT  *  FROM  notice t  where t.id="+param;
        System.out.println("sql="+sql);

    }
}
