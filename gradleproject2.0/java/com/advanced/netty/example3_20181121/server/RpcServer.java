package com.advanced.netty.example3_20181121.server;

import com.advanced.netty.example3_20181121.model.BaseRegistry;
import com.advanced.netty.example3_20181121.model.PublicService;
import com.advanced.netty.example3_20181121.model.RpcRequest;
import com.advanced.netty.example3_20181121.model.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dengbin
 * @date 2016/7/6
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private String serverAddress;
    private BaseRegistry serviceRegistry;
    private List<String> CLASS_NAMES=new ArrayList<>();
    /**
     *  存放接口名与服务对象之间的映射关系
     */
    private volatile Map<String, Object> handlerMap = new HashMap<>();
    /**
     * 存放接口名与服务对象之间的映射关系 按照类名忽略路径
     */
    private volatile Map<String, Object> handlerMapLike = new HashMap<>();

    private static ThreadPoolExecutor threadPoolExecutor;
    /**
     * 读超时
     */
    private static final int READ_IDEL_TIME_OUT = 4;
    /**
     * 写超时
     */
    private static final int WRITE_IDEL_TIME_OUT = 5;
    /**
     * 所有超时
     */
    private static final int ALL_IDEL_TIME_OUT = 7;

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, BaseRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // 获取所有带有 PublicService 注解的 Spring Bean
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(PublicService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(PublicService.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
                if(interfaceName.lastIndexOf(".")>0){
                    handlerMap.put(interfaceName.substring(interfaceName.lastIndexOf(".")+1),serviceBean);
                    CLASS_NAMES.add(interfaceName.substring(interfaceName.lastIndexOf(".")+1));
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread registry=new Thread(){
            @Override
            public void run() {
                if (serviceRegistry != null) {
                    serviceRegistry.register(serverAddress,CLASS_NAMES); // 注册服务地址
                }
            }
        };
        registry.start();
        Thread t1=new Thread(){
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel channel) throws Exception {
                                    channel.pipeline()
                                            .addLast(new RpcDecoder(RpcRequest.class)) // 将 RPC 请求进行解码（为了处理请求）
                                            .addLast(new RpcEncoder(RpcResponse.class)) // 将 RPC 响应进行编码（为了返回响应）
                                            .addLast(new RpcHandler(handlerMap)) // 处理 RPC 请求
                                            .addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                                                    WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                                }
                            })
//                            .option(ChannelOption.SO_BACKLOG, 128);
//                            .childOption(ChannelOption.SO_KEEPALIVE, true)
                            .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 655360, 655360));
//                            .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 65536, 65536));

                    String[] array = serverAddress.split(":");
                    String host = array[0];
                    int port = Integer.parseInt(array[1]);

                    ChannelFuture future = bootstrap.bind(host, port).sync();//开放访问端口
                    LOGGER.debug("server started on port {}", port);
                future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("-------->发生错误");
                } finally {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                }
            }
        };
        t1.start();
    }

    /**
     * 线程池来响应
     * @param task
     */
    public static void submit(Runnable task){
        if(threadPoolExecutor == null){
            synchronized (RpcServer.class) {
                if(threadPoolExecutor == null){
                    threadPoolExecutor = new ThreadPoolExecutor(5, 16, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }
}