package com.j2ee.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;  
  
public class Netty_SimpleClient implements  Runnable {
    private String host="";
    private int port;
    public static Boolean isConnectSuccess=false;
    public Netty_SimpleClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new Netty_SimpleClientHandler());
                }
            });

            // Start the client.
            ChannelFuture channelFuture=b.connect(host, port);
            ChannelFuture f = channelFuture.sync();
            Channel channel=f.channel();
            channelFuture=channel.closeFuture();
            System.out.println("连接成功，等待连接关闭");
            isConnectSuccess=true;
            // Wait until the connection is closed.
            channelFuture.sync();
            System.out.println("连接已关闭");
        }catch (Exception e){
            isConnectSuccess=false;
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Netty_SimpleClient client=new Netty_SimpleClient("127.0.0.1", 1999);
        client.run();
    }
}