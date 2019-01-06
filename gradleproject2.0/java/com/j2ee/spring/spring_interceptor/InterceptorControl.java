package com.j2ee.spring.spring_interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zjm on 2019/1/3.
 */
@Controller
@RequestMapping("springInterceptorController")
public class InterceptorControl {
    @RequestMapping("interceptormethod")
    public String interceptormethod(HttpServletRequest request,HttpServletResponse response){
        System.out.println("controller-------->date:"+request.getParameter("date")+";name:"+request.getParameter("name"));
        return "jsp/springinterceptormain";
    }
}
