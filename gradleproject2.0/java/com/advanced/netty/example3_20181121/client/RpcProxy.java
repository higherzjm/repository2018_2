package com.advanced.netty.example3_20181121.client;


import com.advanced.netty.example3_20181121.model.RpcRequest;
import com.advanced.netty.example3_20181121.model.RpcResponse;
import com.advanced.netty.example3_20181121.util.RespTimeOutException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 代理
 * @author dengbin
 * @date 2016/7/7
 */
public class RpcProxy {

    private String serverAddress;
    private BaseDiscovery serviceDiscovery;
    /**
     * 超时时间  单位秒
     */
    private Long timeOut=0L;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ZookeeperServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
    public RpcProxy(RedisServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public RpcProxy(ZookeeperServiceDiscovery serviceDiscovery, Long timeOut) {
        this.serviceDiscovery = serviceDiscovery;
        this.timeOut=timeOut;
    }
    public RpcProxy(RedisServiceDiscovery serviceDiscovery, Long timeOut) {
        this.serviceDiscovery = serviceDiscovery;
        this.timeOut=timeOut;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 创建并初始化 RPC 请求
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);

                        if (serviceDiscovery != null) {
                            // 发现服务
                            serverAddress = serviceDiscovery.discover(interfaceClass.getSimpleName());
                        }
//                        serverAddress="192.168.1.15:8000";
                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);

                        RpcClient client;
                        //有设置超时时间
                        // 初始化 RPC 客户端
                        if(timeOut>0){
                            client= new RpcClient(host, port,timeOut);
                        }else{
                            client= new RpcClient(host, port);
                        }
                        client.send(request);//初始化netty客户端
                        // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
                        RpcResponse response = client.getResponse();
                        if(response==null){
                           throw new RespTimeOutException("response time out ");
                        }
                        if (response.isError()) {
                            throw response.getError();
                        } else {
                            return response.getResult();
                        }
                    }
                }
        );
    }
}