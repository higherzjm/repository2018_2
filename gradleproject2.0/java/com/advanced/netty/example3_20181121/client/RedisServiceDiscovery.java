package com.advanced.netty.example3_20181121.client;

import com.advanced.netty.example3_20181121.model.RedisMessageModel;
import com.advanced.netty.example3_20181121.util.Constant;
import com.utils.JacksonUtil;
import io.netty.util.internal.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dengbin
 * @date 2016/10/18
 */
public class RedisServiceDiscovery implements BaseDiscovery {
    private static Logger logger = LoggerFactory.getLogger(RedisServiceDiscovery.class);
    private String redisHost;
    private Integer redisPort;

    private JedisPool jedisPool;

    private volatile Map<String,Map<String,Long>> dataMap= new HashMap<>();

    private String password;

    public RedisServiceDiscovery(String registryAddress) {
        String[] str=registryAddress.split(":");
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
     * 按照接口名称查询服务列表
     * @param className
     * @return
     */
    @Override
    public String discover(String className) {
        String data=null;
        Map<String,Long> serverMaps = dataMap.get(className);
        int size = serverMaps.size();
        if (size > 0) {
            List<String> serverLists=new ArrayList<>();
            for(Map.Entry<String, Long> entry:serverMaps.entrySet()){
                if(System.currentTimeMillis()-entry.getValue()>5000){
                    System.out.println("接口信息是超时的,删除host信息");
                    serverMaps.remove(entry.getKey());
                }else{
                    serverLists.add(entry.getKey());
                }
            }
            size=serverLists.size();
                if(size>0){
                    if (size == 1) {
                        data = serverLists.get(0);
                    } else {
                        data = serverLists.get(ThreadLocalRandom.current().nextInt(size));
                    }
                }
        }
     return data;
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

    private JedisPool connectServer(String redisIp, int reidsPort, String password) {
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
            RedisMessageModel redisMessageModel= JacksonUtil.toBeanFromStr(message, RedisMessageModel.class);
            if(redisMessageModel!=null && !redisMessageModel.getClassList().isEmpty()){
                for(String str:redisMessageModel.getClassList()){
                    if(dataMap.containsKey(str)){
                        dataMap.get(str).put(redisMessageModel.getHost(),System.currentTimeMillis());
                    }else{
                        dataMap.put(str,new HashMap<String,Long>(){
                            {
                                put(redisMessageModel.getHost(),System.currentTimeMillis());
                            }
                        });
                    }
                }
            }
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

        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {

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
            while (true){
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
                        jedis.subscribe(subscriber, Constant.REDIS_REGISTRY_PATH);
                        isOpen=true;
                    }
                    Thread.sleep(5000);
                } catch (Throwable throwable) {
                    logger.info("###Redis连接异常,5秒后自动重连:"+throwable.getMessage());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    //链接异常,需要重新订阅
                    isOpen=false;
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
      }

    }
}
