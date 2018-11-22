package com.advanced.netty.example3_20181121.util;

/**
 *
 * @author dengbin
 * @date 2016/7/6
 */
public interface Constant {
    int ZK_SESSION_TIMEOUT = 6000;

    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/dataSource";

   final String REDIS_REGISTRY_PATH = "registry";
}
