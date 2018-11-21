package com.advanced.redis_JedisPubSub.example1;

import com.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zjm on 2018/11/20.
 */
public class JedisPubSubTest {
    private static Logger logger = LoggerFactory.getLogger(JedisPubSubTest.class);
    private String redisHost;
    private Integer redisPort;
    private static Jedis publisherJedis=null;
    private JedisPool jedisPool;

    private volatile Map<String,Map<String,Long>> dataMap= new HashMap<>();

    private String password;

    public JedisPubSubTest(String serverAddress) {
        String[] str=serverAddress.split(":");
        this.redisHost=str[0];
        this.redisPort=Integer.valueOf(str[1]);
        if(str.length==3){
            password=str[2];
        }
        SubThread subThread=new SubThread();
        subThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 连接redis服务,建立连接池
     * @param redisIp
     * @param redisPort
     * @return
     */
    private JedisPool connectServer(String redisIp, int redisPort) {
        return new JedisPool(new JedisPoolConfig(), redisIp, redisPort);
    }

    private JedisPool connectServer(String redisIp,int reidsPort,String password) {
        return new JedisPool(new JedisPoolConfig(), redisIp, reidsPort,1000000,password);
    }
    /**
     * 订阅监听
     */
    public class Subscriber extends JedisPubSub {
        public Subscriber() {
        }

        @Override
        public void onMessage(String channel, String message) {
            System.out.println(String.format("onMessage 接收消息 Message. Channel: %s, Msg: %s", channel, message));

        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println(String.format("receive redis onPMessage message, channel %s, message %s", channel, message));
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                    channel, subscribedChannels));
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                    channel, subscribedChannels));

        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
            logger.info("pattern:"+pattern+";subscribedChannels:"+subscribedChannels);
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            logger.info("pattern:"+pattern+";subscribedChannels:"+subscribedChannels);
        }
    }

    public class SubThread extends Thread {
        private final Subscriber subscriber = new Subscriber();


        public SubThread() {
            super("SubThread");
        }
        private boolean isOpen=false;
        @Override
        public void run() {
            int i=0;
            while (!isOpen){
                i++;
                Jedis jedis = null;
                try {
                    //当连接处于关闭状态
                    if(jedisPool==null || jedisPool.isClosed()){
                        if(password==null || "".equals(password.trim()) ){
                            jedisPool=connectServer(redisHost,redisPort);
                        }else{
                            jedisPool=connectServer(redisHost,redisPort,password);
                        }
                    }
                    //是否需要执行订阅操作
                    if(!isOpen){
                        jedis = jedisPool.getResource();
                        publisherJedis = jedisPool.getResource();
                        jedis.subscribe(subscriber, "china");//--->调用onSubscribe()方法
                        isOpen=true;
                    }
                } catch (Exception e) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    if (i>=5) {
                        isOpen = true;
                    }
                    e.printStackTrace();
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }

    }

    public static void main(String[] args){
        new JedisPubSubTest("127.0.0.1:6379");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publisherJedis.publish("china", "先发一条初始信息aaaaaaaaaaaaaa");
    }
}
