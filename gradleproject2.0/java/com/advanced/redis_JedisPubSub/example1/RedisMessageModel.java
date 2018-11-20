package com.advanced.redis_JedisPubSub.example1;

import java.io.Serializable;
import java.util.List;

public class RedisMessageModel implements Serializable {
    private String host;
    private List<String> classList;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }
}