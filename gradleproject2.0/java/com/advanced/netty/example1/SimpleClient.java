package com.advanced.netty.example1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {  
      
    public void connect(String host, int port) throws Exception {  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(workerGroup);  
            b.channel(NioSocketChannel.class);  
            b.option(ChannelOption.SO_KEEPALIVE, true);  
            b.handler(new ChannelInitializer<SocketChannel>() {  
                @Override  
                public void initChannel(SocketChannel ch) throws Exception {  
                    ch.pipeline().addLast(new SimpleClientHandler());
                }  
            });  
  
            // Start the client.
            ChannelFuture channelFuture=b.connect(host, port);
            ChannelFuture f = channelFuture.sync();
            System.out.println("连接成功");
  
            // Wait until the connection is closed.
            Channel channel=f.channel();
            channelFuture=channel.closeFuture();
            System.out.println("channel.closeFuture");
            channelFuture.sync();
            System.out.println("Wait until the connection is closed");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        SimpleClient client=new SimpleClient();
        client.connect("127.0.0.1", 1999);
    }  
      
}  