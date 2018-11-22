package com.advanced.netty.example3_20181121.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dengbin
 * @date 2016/10/18
 */
public class RedisMessageModel implements Serializable{
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
