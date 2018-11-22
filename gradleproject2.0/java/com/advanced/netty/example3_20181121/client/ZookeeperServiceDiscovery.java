package com.advanced.netty.example3_20181121.client;

import com.advanced.netty.example3_20181121.util.Constant;
import io.netty.util.internal.ThreadLocalRandom;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author dengbin
 * @date 2016/7/7
 */
public class ZookeeperServiceDiscovery implements BaseDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<>();

    private volatile Map<String,List<String>> dataMap= new HashMap<>();

    private String registryAddress;

    private ZooKeeper zookeeperOnlyOne;

    public ZookeeperServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
        //连接zookeeper
        zookeeperOnlyOne = connectServer();
        if (zookeeperOnlyOne != null) {
            watchNode(zookeeperOnlyOne);
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
        List<String> serverLists = dataMap.get(className);
        int size = serverLists.size();
        if (size > 0) {
            if (size == 1) {
                data = serverLists.get(0);
                LOGGER.debug("using only data: {}", data);
            } else {
                data = serverLists.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random data: {}", data);
            }
        }
        return data;
    }


    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            dataMap.clear();
            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                List<String> serverList = zk.getChildren(Constant.ZK_REGISTRY_PATH + "/" + node,new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (event.getType() == Event.EventType.NodeChildrenChanged) {
//                            watchNode(zk);
                        }
                    }
                });
                if(serverList.size()>0){
                    List<String> childList=new ArrayList<>();
                    for(String childNode:serverList){
                        System.out.println("==="+ Constant.ZK_REGISTRY_PATH + "/" + node + "/" + childNode);
                        byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node + "/" + childNode, false, null);
                        childList.add(new String(bytes));
                    }
                    dataMap.put(node,childList);
                }
            }
            LOGGER.debug("node data: {}", dataMap);
            this.dataList = dataList;
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }

}
