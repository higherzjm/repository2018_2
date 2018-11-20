package com.advanced.redis_JedisPubSub.example2;

import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPubSub;

/**
 * 订阅者
 */
public class Subscriber extends JedisPubSub {//注意这里继承了抽象类JedisPubSub

    private static final Logger LOGGER = Logger.getLogger(Subscriber.class);

    @Override
    public void onMessage(String channel, String message) {
        LOGGER.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
    	LOGGER.info(String.format("PMessage. Pattern: %s, Channel: %s, Msg: %s", 
    	    pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

        LOGGER.info("onSubscribe 订阅  channel:"+channel+" ;subscribedChannels:"+subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

        LOGGER.info("onUnsubscribe 取消订阅 channel:"+channel+" ;subscribedChannels:"+subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

        LOGGER.info("onPUnsubscribe  channel:"+pattern+" ;subscribedChannels:"+subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

        LOGGER.info("onPSubscribe  channel:"+pattern+" ;subscribedChannels:"+subscribedChannels);
    }
}