package com.j2ee.servlet.listeners;

import com.j2ee.netty.Netty_SimpleServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zjm on 2018/11/6.
 */
public class NettyListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            new Netty_SimpleServer(2018).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
