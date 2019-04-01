package com.advanced.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  * RabbitMQ 生产者
 *  * 
 *  
 */
public class rabbitmq_Producer {
    //队列名称
    private final static String QUEUE_NAME = "Queue";

    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory factory = null;
        // 建立到代理服务器到连接
        Connection connection = null;
       // 获得通道
        Channel channel = null;
        try {
            factory = new ConnectionFactory();
            //设置用户、和密码、端口号、IP地址、和虚拟机名称
           /* factory.setUsername("root");
            factory.setPassword("123456");
            factory.setHost("192.168.1.11");
            factory.setPort(5672);
            factory.setVirtualHost("root_vhost");*/
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setHost("localhost");
            factory.setPort(5672);
            //factory.setVirtualHost("root_vhost");
             // 建立到代理服务器到连接
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world 努力，效率---快下班了";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("发送  message[" + message + "] to " + QUEUE_NAME + " success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
            // 关闭资源
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
