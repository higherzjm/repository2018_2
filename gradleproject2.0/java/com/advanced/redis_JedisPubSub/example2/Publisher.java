package com.advanced.redis_JedisPubSub.example2;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 发布者
 */
public class Publisher {

    private static final Logger LOGGER = Logger.getLogger(Publisher.class);
    private final Jedis publisherJedis;
    private final String channel;

    public Publisher(Jedis publisherJedis, String channel) {
        this.publisherJedis = publisherJedis;
        this.channel = channel;
    }

    /**
     * 不停的读取输入，然后发布到channel上面，遇到quit则停止发布。
     */
    public void startPublish() {
        LOGGER.info("Type your message (quit for terminate)");
        try {
            publisherJedis.publish(channel, "先发一条初始信息aaaaaaaaaaaaaa");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = reader.readLine();
                if (!"quit".equals(line)) {
                    publisherJedis.publish(channel, line);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("IO failure while reading input", e);
        }
    }
}