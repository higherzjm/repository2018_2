package com.j2ee.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@Controller
@RequestMapping(value ="/send_redis_merssagecontroller")
public class Send_REDIS_MessageController {

    @Resource(name="redisTemplate")
    private RedisTemplate<String, String> redisTemplate;


    public void sendMessage(String channel, Serializable message) {

        redisTemplate.convertAndSend(channel, message);//channel消息监听名称必须与配置文件配置的一样
    }
    //http://localhost:8080/repository2018_2/send_redis_merssagecontroller/sendmsg.do
    @RequestMapping(value = "sendmsg")
    @ResponseBody
    public  String sendMessage(){
       try{
           MessageVo messageVo = new MessageVo();
           messageVo.setDate("20171207");
           messageVo.setKey("tuofuwebservices");
           List<String> webmatids=new ArrayList<String>();
           webmatids.add("123"); webmatids.add("456");
           Map<String,List<String>> webmaidmtaids=new HashMap<String, List<String>>();
           webmaidmtaids.put("1147",webmatids);
           messageVo.setWebsitematids(webmaidmtaids);

           redisTemplate.opsForValue().set("name","张三");//设置值
           redisTemplate.opsForSet().add("set_123", "set1","set2","set3");//设置set集合
           /**
            * 设置list  begin
            */

           List<String> list1=new ArrayList<String>();
           list1.add("a1");
           list1.add("a2");
           list1.add("a3");

           List<String> list2=new ArrayList<String>();
           list2.add("b1");
           list2.add("b2");
           list2.add("b3");
           redisTemplate.opsForList().leftPush("listkey1", String.valueOf(list1));
           redisTemplate.opsForList().rightPush("listkey2", String.valueOf(list2));

           /**
            * 设置list  end
            */
            /**
             * 设置map  begin
             */
           Map<String,String> map=new HashMap<>();
           map.put("key1","value1");
           map.put("key2","value2");
           map.put("key3","value3");
           map.put("key4","value4");
           map.put("key5","value5");
           redisTemplate.opsForHash().putAll("map",map);
           /**
            * 设置map  end
            */

           //异步发送短信到redis队列
           sendMessage("msgname", messageVo);//"java",和applicationContext_redis中topic配置一样
           return "发送成功";
       }catch (Exception e){
           e.printStackTrace();
           return "发送失败";
       }
    }

    //http://localhost:8080/repository2018_2/send_redis_merssagecontroller/getmsg.do
    @RequestMapping(value = "getmsg")
    @ResponseBody
    public  String getMessage(){
        String name=redisTemplate.opsForValue().get("name");//获取值
        System.out.println("name:"+name);

        Set<String> set=redisTemplate.opsForSet().members("set_123");
        System.out.println("set:"+set);

        String list1=redisTemplate.opsForList().leftPop("listkey1");
        String list2=redisTemplate.opsForList().rightPop("listkey2");
        System.out.println("list1:"+list1);
        System.out.println("list2:"+list2);


        Map<Object, Object> map= redisTemplate.opsForHash().entries("map");
        List<Object> resultMapList=redisTemplate.opsForHash().values("map");
        Set<Object> resultMapSet=redisTemplate.opsForHash().keys("map");
        String value=(String)redisTemplate.opsForHash().get("map","key1");
        System.out.println("map:"+map);
        System.out.println("resultMapList:"+resultMapList);
        System.out.println("resultMapSet:"+resultMapSet);
        System.out.println("value:"+value);

        return "name:"+name+";set:"+set+";list:"+list1+";map:"+map;
    }




}