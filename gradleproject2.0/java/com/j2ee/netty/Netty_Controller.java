package com.j2ee.netty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zjm on 2018/11/6.
 */
@Controller
@RequestMapping(value ="/nettyController")
public class Netty_Controller {

    /**
     * 客户端连接
     * @return
     */
    @RequestMapping(value = "/clientConnect")
    @ResponseBody
    public  String clientConnect() {
        try {
            new Thread(new Netty_SimpleClient("127.0.0.1", 1999)).start();//不能跟tomcat的端口号一样
            Thread.sleep(3000);
            if (Netty_SimpleClient.isConnectSuccess){
                System.out.println("连接成功");
                return "连接成功";
            }else {
                System.out.println("连接失败");
                return "连接失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "连接失败";
        }
    }

    /**
     * 服务启动
     * @return
     */
    @RequestMapping(value = "/serverLaunch")
    @ResponseBody
    public  String serverLaunch() {
        try {
            new Thread(new Netty_SimpleServer(1999)).start();//不能跟tomcat的端口号一样
            Thread.sleep(3000);
            if (Netty_SimpleServer.isStartSuccess){
                System.out.println("启动成功");
                return "启动成功";
            }else {
                System.out.println("启动失败");
                return "启动失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "启动失败";
        }
    }

    /**
     * 消息发送
     * @return
     */
    @RequestMapping(value = "/senMsg")
    @ResponseBody
    public String senMsg(HttpServletRequest request, HttpServletResponse response){
        String msg=request.getParameter("msg");
        Netty_SimpleClientHandler.sendMsg(msg);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String receiveMsg=Netty_SimpleClientHandler.receiveMsg;
        System.out.println("返回消息:"+receiveMsg);
        return receiveMsg;
    }
}
