package com.j2ee.spring.spring_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {
        String  servletPath=httpRequest.getServletPath();
        if ("/springaopmaincontroller/test1.do".equals(servletPath)){
            System.out.println(new Date()+":afterCompletion请求后:"+httpRequest.getServletPath()+";ParameterMap:"+httpRequest.getParameterMap());
            String value=httpRequest.getParameter("value");
           System.out.println("value:"+value);
        }


         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {

        String  servletPath=request.getServletPath();
        if ("/springaopmaincontroller/test1.do".equals(servletPath)){
            System.out.println(new Date()+":preHandle请求前:"+request.getServletPath()+";ParameterMap:"+request.getParameterMap());
            String value=request.getParameter("value");
            if ("0".equals(value)){
                return false;//无法进行成功跳转
            }else if ("1".equals(value)){//目标代码存在异常的情况
                request.setAttribute("value","the value of update");
                return true;
            }else if ("2".equals(value)){//目标代码正常执行的情况
                return true;
            }
        }



        Map<String,String[]>  datas=request.getParameterMap();
        if ("springaopmaincontroller/test2.do".equals(request.getServletPath())){
            System.out.println(new Date()+":springaopmaincontroller/test2.do-------->name:"+request.getParameter("name")+";age:"+request.getParameter("age"));
            return true;
        }else {
            return true;
        }
    }

}