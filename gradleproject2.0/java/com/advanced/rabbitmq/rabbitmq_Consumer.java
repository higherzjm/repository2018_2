package com.advanced.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

/**
 *  * RabbitMQ 消费者
 *  * @author Administrator
 *  *
 *  
 */
public class rabbitmq_Consumer {
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
            //设置用户、和密码、端口号、虚拟机名称、和IP地址
            factory = new ConnectionFactory();
            /*factory.setUsername("root");
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
            // 1.队列名2.是否持久化，3是否局限与链接，4不再使用是否删除，5其他的属性
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 声明一个消费者,配置好获取消息的方式
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);
            // 循环获取消息
            while (true) {
             // 循环获取信息
             // 指向下一个消息，如果没有会一直阻塞
                Delivery delivery = consumer.nextDelivery();
                String msg = new String(delivery.getBody());
                System.out.println("接收 message[" + msg + "] from " + QUEUE_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ShutdownSignalException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
