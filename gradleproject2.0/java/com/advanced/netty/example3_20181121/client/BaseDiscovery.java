package com.advanced.netty.example3_20181121.client;

/**
 *
 * @author dengbin
 * @date 2016/10/18
 */
public interface BaseDiscovery {
    /**
     * 服务发现
     * 通过类名称 --> 找到实体类 -->找到实现类
     * @param className
     * @return
     */
    public String discover(String className);
}
