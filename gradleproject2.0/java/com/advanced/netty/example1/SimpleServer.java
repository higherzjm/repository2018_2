package com.advanced.netty.example1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/** 
 *  
 * Netty中，通讯的双方建立连接后，会把数据按照ByteBuf的方式进行传输， 
 * 例如http协议中，就是通过HttpRequestDecoder对ByteBuf数据流进行处理，转换成http的对象。 
 *  
 */  
public class SimpleServer {  
    private int port;  
  
    public SimpleServer(int port) {  
        this.port = port;  
    }  
  
    public void run() throws Exception {  
        //EventLoopGroup是用来处理IO操作的多线程事件循环器  
        //bossGroup 用来接收进来的连接  
        EventLoopGroup bossGroup = new NioEventLoopGroup();   
        //workerGroup 用来处理已经被接收的连接  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            //启动 NIO 服务的辅助启动类  
            ServerBootstrap b = new ServerBootstrap();   
            b.group(bossGroup, workerGroup)
                //配置 Channel  
                .channel(NioServerSocketChannel.class)  
                .childHandler(new ChannelInitializer<SocketChannel>() {   
                        @Override  
                        public void initChannel(SocketChannel ch) throws Exception {  
                            // 注册handler    
                            ch.pipeline().addLast(new SimpleServerHandler());
                        }  
                    })  
                .option(ChannelOption.SO_BACKLOG, 128)   
                .childOption(ChannelOption.SO_KEEPALIVE, true);   
  
            // 绑定端口，开始接收进来的连接  
            ChannelFuture f = b.bind(port).sync();
            Channel channel=f.channel();
            ChannelFuture channelFuture=channel.closeFuture();
            System.out.println("启动成功,等待服务器 socket 关闭");
            // 等待服务器 socket 关闭 。  
            channelFuture.sync();
            System.out.println("服务器 socket 已关闭");
        }catch (Exception e){
            System.out.println("启动失败");
            e.printStackTrace();
        }finally {
            //关闭事件流组
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        new SimpleServer(1999).run();
    }  
}  