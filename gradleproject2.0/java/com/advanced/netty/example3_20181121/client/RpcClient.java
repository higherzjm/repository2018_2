package com.advanced.netty.example3_20181121.client;

import com.advanced.netty.example3_20181121.model.RpcRequest;
import com.advanced.netty.example3_20181121.model.RpcResponse;
import com.advanced.netty.example3_20181121.server.RpcDecoder;
import com.advanced.netty.example3_20181121.server.RpcEncoder;
import com.utils.JacksonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dengbin
 * @date 2016/7/7
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);

    private String host;
    private int port;
    private Long timeOut=0L;
    private RpcResponse response;
    private static final int READ_IDEL_TIME_OUT = 4; // 读超时
    private static final int WRITE_IDEL_TIME_OUT = 5;// 写超时
    private static final int ALL_IDEL_TIME_OUT = 7; // 所有超时

    private final Object obj = new Object();

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RpcClient(String host, int port,Long timeOut) {
        this.host = host;
        this.port = port;
        this.timeOut=timeOut;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        this.response = response;
        System.out.println("||"+ JacksonUtil.toStrFromBean(response));
//        synchronized (obj) {
//            if(timeOut<=0){
//                obj.notifyAll(); // 收到响应，唤醒线程
//            }
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("com.paytos.dengb.rpc.client caught exception", cause);
        ctx.close();
    }

    public void send(RpcRequest request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(24, 1024, 65538));
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new RpcEncoder(RpcRequest.class)) // 将 RPC 请求进行编码（为了发送请求）
                                    .addLast(new RpcDecoder(RpcResponse.class)) // 将 RPC 响应进行解码（为了处理响应）
                                    .addLast("readTimeOut", new ReadTimeoutHandler(60))
                                    .addLast(RpcClient.this);
                            /*
                                    .addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                                    WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS))
                                    .addLast(new HeartbeatServerHandler());
                             */
                        }
                    });


            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().writeAndFlush(request).sync();

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public RpcResponse getResponse() throws InterruptedException {
        if(response!=null){
            return response;
        }else{
            if(timeOut==null||timeOut==0){
                timeOut=30L;
            }
            for(Long initTime=System.currentTimeMillis();System.currentTimeMillis()-initTime<timeOut*1000;){
                if(response==null){
                       Thread.sleep(100);
                  }else{
                    return this.response;
                }
             }
        }
        return null;
    }
}
