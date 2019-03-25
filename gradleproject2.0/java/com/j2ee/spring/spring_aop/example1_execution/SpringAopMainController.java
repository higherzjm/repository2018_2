package com.j2ee.spring.spring_aop.example1_execution;

import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zjm on 2019/1/3.
 */
@Controller
@RequestMapping("springaopmaincontroller")
public class SpringAopMainController {

    @RequestMapping("mainindex")
    public String main(){
        return "jsp/springaopmain";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public Result test(@RequestParam String value,HttpServletRequest request,HttpServletResponse response) throws Exception {

       String value2= (String) request.getAttribute("value");//获取拦截器设置的参数
       String value3=request.getParameter("value");
        // 示例1，目标方法抛出异常的情况
        if ("1".equals(value)) {
            System.out.println(new Date()+":出现异常了 value2:"+value2+";value:"+value);
            throw new Exception("出现异常了");
        }

        // 示例 2，目标方法执行正常的情况
        System.out.println(new Date()+": test OK value2:"+value2+";value:"+value);
        return new Result() {{
            this.setId(111);
            this.setName("my name is allen ");
        }};
    }

    public static class Result {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @RequestMapping(value = "test2", method = RequestMethod.POST)
    @ResponseBody
    public String test2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name=request.getParameter("name");
        String age=request.getParameter("age");
        String school= request.getSession().getAttribute("school")==null?
                "":request.getSession().getAttribute("school").toString();
        System.out.println("我是中国人--->name:"+name+";age:"+age+";school:"+school);
        //抛出异常了
        if ("李四".equals(name)){
            throw  new Exception();
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name","李四");
        jsonObject.put("age",100);
        return jsonObject.toString();
    }

    @RequestMapping(value = "unPointcut", method = RequestMethod.POST)
    @ResponseBody
    public String unPointcut(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        String age=request.getParameter("age");
        System.out.println("我是中国人2--->name:"+name+";age:"+age);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name","李四");
        jsonObject.put("age",100);
        return jsonObject.toString();
    }

}
