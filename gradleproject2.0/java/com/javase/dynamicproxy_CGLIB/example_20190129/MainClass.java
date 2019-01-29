package com.javase.dynamicproxy_CGLIB.example_20190129;

import org.junit.Test;

public class MainClass {
    @Test
    public void  test1(){
        UserServiceCglib cglib = new UserServiceCglib();
        UserServiceImpl bookFacedImpl = (UserServiceImpl) cglib.getInstance(new UserServiceImpl());
        bookFacedImpl.addUser();
    }
}