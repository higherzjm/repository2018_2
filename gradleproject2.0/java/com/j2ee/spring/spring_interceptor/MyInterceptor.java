package com.j2ee.spring.spring_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advanced.Properties.ZjmProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {

        String servletPath=request.getPathInfo();
        System.out.println("servletPath1:"+servletPath);
        servletPath=request.getServletPath();
        System.out.println("servletPath2:"+servletPath);
        String  injectionStr= ZjmProperties.getKeyValue("InjectionStr");
        System.out.println(new Date()+":拦截器，--防注入的配置值："+injectionStr);
        Map<String,String[]> datas=request.getParameterMap();
        Set<Map.Entry<String, String[]>> set = datas.entrySet();
        Iterator<Map.Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> entry = it.next();
            for (String paramValue : entry.getValue()) {
                if (injectionCheck(injectionStr,paramValue)){
                    System.out.println(new Date()+"拦截器，被拦截的参数名称:"+entry.getKey());
                    System.out.println(new Date()+"拦截器，被拦截的参数值:"+paramValue);
                    System.out.println("拦截器，被拦截的请求路径:"+servletPath);
                    return false;
                }
            }
        }

        System.out.println(new Date()+"拦截器，验证通过，请求路径:"+servletPath);
        String pathInfo=request.getPathInfo();
        System.out.println("pathInfo:"+pathInfo);
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

    /**
     *
     * @param injectionStr 防注入可配置的字符串
     * @param ParamValue  页面上传的参数值
     * @return  返回true 表示包含注入的字符,需要拦截请求，返回false表示通过，不包含注入字符
     */
    public boolean injectionCheck(String  injectionStr,String ParamValue){
        for(int i=0;i<injectionStr.length();i++){
            String subStr = injectionStr.substring(i, i+1);
            if (ParamValue.contains(subStr)){
                return true;
            }
        }

        return false;
    }
}