package com.advanced.netty.example3_20181121.model;

import java.util.List;

/**
 *
 * @author dengbin
 * @date 2016/10/18
 */
public interface BaseRegistry {

    /**
     * 向注册中心提交所提供的服务
     * @param serverHost
     * @param classNames
     */
    public void register(String serverHost, List<String> classNames);

}
