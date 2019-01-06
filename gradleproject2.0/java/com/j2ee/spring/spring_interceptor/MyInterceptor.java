package com.j2ee.spring.spring_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("afterCompletion请求后:"+httpRequest.getServletPath()+";ParameterMap:"+httpRequest.getParameterMap());

         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {
        System.out.println("preHandle请求前:"+request.getServletPath()+";ParameterMap:"+request.getParameterMap());
        if ("/springInterceptorController/interceptormethod2.do".equals(request.getServletPath())){
            System.out.println("HandlerInterceptor-------->date:"+request.getParameter("date")+";name:"+request.getParameter("name"));
            return false;//无法进行成功跳转
        }else {
            return true;
        }
    }

}