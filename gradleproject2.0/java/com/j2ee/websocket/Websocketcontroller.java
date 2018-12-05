package com.j2ee.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zjm on 2018/12/5.
 */
@Controller
@RequestMapping("/websocketcontroller")
public class Websocketcontroller {

    @RequestMapping("/index")
    public String index(){
        return "jsp/websocket";
    }
}
