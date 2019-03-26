package com.j2ee.spring.spring_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        Map<String,String[]>  datas=request.getParameterMap();
        if ("/springaopmaincontroller/test1.do".equals(servletPath)){
            String value=request.getParameter("value");
            System.out.println(new Date()+":preHandle请求前:"+servletPath+";ParameterMap:"+datas+";value:"+value);
          //读取客户端传的参数值
            if ("0".equals(value)){
                return false;//无法进行成功跳转
            }else if ("1".equals(value)){//目标代码存在异常的情况
                request.setAttribute("value","the value of update");//设置参数值
                return true;
            }else if ("2".equals(value)){//目标代码正常执行的情况
                return true;
            }
        }




        if ("/springaopmaincontroller/test2.do".equals(servletPath)){
            for(String key:datas.keySet()){
                System.out.println("KEY:"+key);
            }
            Set<Map.Entry<String, String[]>> set = datas.entrySet();
            Iterator<Map.Entry<String, String[]>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String[]> entry = it.next();
                System.out.println("KEY:"+entry.getKey());
                for (String i : entry.getValue()) {
                    System.out.println("VALUE:"+i);
                }
            }
            System.out.println(new Date()+":preHandle请求前:"+servletPath+";ParameterMap:"+datas);
            System.out.println(new Date()+":springaopmaincontroller/test2.do-------->name:"+request.getParameter("name")+";age:"+request.getParameter("age"));
            return true;
        }else {
            return true;
        }
    }

}