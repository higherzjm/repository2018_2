package com.j2ee.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
public class Netty_SimpleServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端发送消息后就会进入该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];  
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中  
        result.readBytes(result1);  
        String resultStr = new String(result1);  
        // 接收并打印客户端的信息  
        System.out.println("Client said:" + resultStr);  
        // 释放资源，这行很关键  
        result.release();  
  
        // 向客户端发送消息  
        String response = resultStr+"--->hello client,服务端返回消息了!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组  
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());  
        encoded.writeBytes(response.getBytes());  
        ctx.write(encoded);  
        ctx.flush();  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        // 当出现异常就关闭连接  
        cause.printStackTrace();  
        ctx.close();  
    }

    /**
     * 读取数据完成会进行缓存刷新
     * @param ctx
     * @throws Exception
     */
    @Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        ctx.flush();  
    }  
  
}  