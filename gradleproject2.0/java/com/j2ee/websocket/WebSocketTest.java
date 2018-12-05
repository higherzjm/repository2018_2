package com.j2ee.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket")
public class WebSocketTest {
    public WebSocketTest() {
        System.out.println("初始化WebSocketTest");//连接的时候初始化
    }

    @OnMessage
    public void onMessage(String message)
            throws Exception {
        System.out.println("onMessage:"+message);
    }
    @OnOpen
    public void onOpen (Session session) {
        System.out.println("onOpen建立连接时会进来:"+session);
        try {
            session.getBasicRemote().sendText("onOpenData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose () {
        System.out.println("onClose");
    }
    @OnError
    public void onError(Throwable throwable){
        System.out.print("onError");
        throwable.printStackTrace();
    }
}