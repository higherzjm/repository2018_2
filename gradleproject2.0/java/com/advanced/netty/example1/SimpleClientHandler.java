package com.advanced.netty.example1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务端发送消息后就会进入该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        System.out.println("Netty_SimpleClientHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;    
        byte[] result1 = new byte[result.readableBytes()];    
        result.readBytes(result1);    
        System.out.println("Server said:" + new String(result1));    
        result.release();    
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        // 当出现异常就关闭连接  
        cause.printStackTrace();  
        ctx.close();  
    }


    /**
     * 连接成功就会进入该方法
     *  连接成功后，向server发送消息
      * @param ctx
     * @throws Exception
     */
    @Override    
    public void channelActive(ChannelHandlerContext ctx) throws Exception {    
        String msg = "hello Server!我给你发消息了";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());    
        encoded.writeBytes(msg.getBytes());    
        ctx.write(encoded);
        ctx.flush();    
    }    
} 