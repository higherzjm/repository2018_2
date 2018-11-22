package com.advanced.netty.example3_20181121.server;

import com.advanced.netty.example3_20181121.model.RpcRequest;
import com.advanced.netty.example3_20181121.model.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.util.Map;

/**
 *
 * @author dengbin
 * @date 2016/7/7
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
//        RpcResponse response = new RpcResponse();
//        response.setRequestId(request.getRequestId());
//        try {
//            Object result = handle(request);
//            response.setResult(result);
//        } catch (Throwable t) {
//            response.setError(t);
//        }
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

        RpcServer.submit(new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Receive request " + request.getRequestId());
                RpcResponse response = new RpcResponse();
                response.setRequestId(request.getRequestId());
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setError(t);
                    LOGGER.error("RPC Server handle request error",t);
                }
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        LOGGER.debug("Send response for request " + request.getRequestId());
                    }
                });
            }
        });
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        if(serviceBean==null){
            className=className.substring(className.lastIndexOf(".")+1);
            serviceBean = handlerMap.get(className);
        }


        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("com.paytos.dengb.rpc.server caught exception", cause);
        ctx.close();
    }
}
