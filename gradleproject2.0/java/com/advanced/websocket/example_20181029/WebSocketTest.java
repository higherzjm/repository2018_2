package com.advanced.websocket.example_20181029;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
public class WebSocketTest {
    @OnMessage
    public void onMessage(String message)
            throws Exception {
        System.out.println("onMessage:"+message);
    }
    @OnOpen
    public void onOpen (Session session) {
        System.out.println("onOpen:"+session);
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