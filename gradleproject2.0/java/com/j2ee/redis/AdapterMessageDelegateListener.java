package com.j2ee.redis;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 监听接收信息的类
 */
@Component("adapterMessageDelegateListener")
public class AdapterMessageDelegateListener {

    //监听Redis消息,监听方法名称需要与配置的一样
    public void handleMessage(Serializable message){
        System.out.println("消息监听----redis消息队列接收消息");
        if(message instanceof MessageVo){
            MessageVo messageVo = (MessageVo) message;//强制转化为发送的消息model
            System.out.println(messageVo.getKey()+":"+messageVo.getDate());
            Map<String,List<String>> webmatids= messageVo.getWebsitematids();
            for (Map.Entry<String,List<String>> data:webmatids.entrySet()){
                System.out.println(data.getKey());
                System.out.println(data.getValue());
            }

        }
    }

}