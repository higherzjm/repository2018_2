package com.j2ee.spring.spring_jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.JacksonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by zjm on 2019/1/18.
 */
@Controller
@RequestMapping("jacksonController")
public class Test_0118 {
    private static ObjectMapper objectMapper;
    @RequestMapping("index")
    public String index(){

        return "jsp/jackson";
    }
    @ResponseBody
    @RequestMapping("test1")
    public void test1(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> mapResult=new HashMap<>();
        mapResult.put("status",false);
        mapResult.put("msg","时间格式错误,格式为yyyy-MM-dd");
        mapResult.put("total",0);

        List list=new ArrayList(){};
        list.add("普洱");
        list.add("大红袍");
        mapResult.put("list", list);

        Link link = new Link();
        link.setAddress("河南省济源市");
        link.setPhone("13899995555");
        link.setQq("123456");
        mapResult.put("link", link);

        Set set=new HashSet();
        set.add("篮球");
        set.add("足球");
        set.add("乒乓球");
        mapResult.put("set", set);

        response.setContentType("text/html;charset=UTF-8");
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        try {
            objectMapper.writeValue(response.getOutputStream(), mapResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }


}
