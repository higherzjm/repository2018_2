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
        Netty_SimpleClient client=new Netty_SimpleClient();
        try {
            client.connect("127.0.0.1", 8080);
            return "连接成功";
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
            new Netty_SimpleServer(8080).run();//不能跟tomcat的端口号一样
            System.out.println("启动成功");
            return "启动成功";
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
        String receiveMsg=Netty_SimpleClientHandler.receiveMsg;
        return receiveMsg;
    }
}
