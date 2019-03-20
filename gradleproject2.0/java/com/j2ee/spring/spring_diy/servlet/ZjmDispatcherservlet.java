package com.j2ee.spring.spring_diy.servlet;


import com.j2ee.spring.spring_diy.annotation.ZjmAutowired;
import com.j2ee.spring.spring_diy.annotation.ZjmController;
import com.j2ee.spring.spring_diy.annotation.ZjmRequestMapping;
import com.j2ee.spring.spring_diy.annotation.ZjmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zjm on 2019/3/18.
 */
public class ZjmDispatcherservlet extends HttpServlet{

    private Properties contextConfig=new Properties();
    private List<String> classNames=new ArrayList<>();
    private Map<Object,Object>  ioc=new HashMap<>();
    private List<Handler> handlerMapping=new ArrayList<Handler>();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this. doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req,resp);
        }catch (Exception e){
            resp.getWriter().write("500"+e.getStackTrace());
        }

    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws  Exception{
        String  uri=req.getRequestURI();
        String  contextPath=req.getContextPath();
        uri=uri.replace(contextPath,"").replaceAll("/+","/");
        if (!handlerMapping.contains(new Handler())){
            resp.getWriter().write("404  not  found!!");
        }
        Handler handler=this.handlerMapping.get(0);
        Map<String, String[]> params=req.getParameterMap();

    }

    @Override
    public void init(ServletConfig config){
        try {
            //1 加载配置文件
            doLoadConfig(config.getInitParameter("contextConfigLocation"));
           //2 扫描到所有的相关类
            doScanner(contextConfig.getProperty("scanPackage"));
           //3 初始化所有相关的类
            doInstance();
           //4 实现自动化的依赖注入
            doAutowired();
            //5 初始化HandlerMapping
            initHandlerMapping();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()){
            return;
        }
        for (Map.Entry<Object,Object> entry:ioc.entrySet()) {
            Class<?>  clazz=entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(ZjmController.class)){continue;}
            String  baseUrl="";
            if (clazz.isAnnotationPresent(ZjmRequestMapping.class)){
                ZjmRequestMapping zjmRequestMapping=clazz.getAnnotation(ZjmRequestMapping.class);
                baseUrl=zjmRequestMapping.value();
            }

            for (Method method:clazz.getMethods()){
                if (!method.isAnnotationPresent(ZjmRequestMapping.class)){ continue;}
                ZjmRequestMapping zjmRequestMapping=method.getAnnotation(ZjmRequestMapping.class);
                String  regex=(baseUrl+"/"+zjmRequestMapping.value()).replaceAll("/+","/");
                Pattern pattern=Pattern.compile(regex);
                handlerMapping.add(new Handler(entry.getValue(),method,pattern));
                System.out.println("mapped:"+regex+","+method);
            }


        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()){
            return;
        }

        for (Map.Entry<Object,Object> entry:ioc.entrySet()){
            Field[]  fields=entry.getValue().getClass().getDeclaredFields();
            for (Field field:fields){
                if (!field.isAnnotationPresent(ZjmAutowired.class)){
                    continue;
                }
                ZjmAutowired zjmAutowired=field.getAnnotation(ZjmAutowired.class);
                String beanName=zjmAutowired.value().trim();
                if ("".equals(beanName)) {
                    beanName=field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            }

        }
        

    private void doInstance() {
        if (classNames.isEmpty()){
            return;
        }
        try {
        String beanName=null;
        for (String className:classNames){
            Class<?> clazz= Class.forName(className);
            //判断，不是所有的类都要初始化
            if (clazz.isAnnotationPresent(ZjmController.class)){
                 Object instance=clazz.newInstance();
                 beanName=lowerFirstCase(clazz.getSimpleName());
                 ioc.put(beanName,instance);

            }else if (clazz.isAnnotationPresent(ZjmService.class)){
                /**
                 * 1 默认使用首字母小写
                 * 2 优先使用自定义beanId
                 * 3 key 接口的type
                 */
                 ZjmService zjmService=clazz.getAnnotation(ZjmService.class);
                 beanName=zjmService.value();
                if ("".equals(beanName.trim())){
                    beanName=lowerFirstCase(clazz.getSimpleName());
                }

                Object  instance=clazz.newInstance();
                ioc.put(beanName,instance);


            }else {
                //忽略
            }
        }



        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    public String lowerFirstCase(String str){
        char[] chars=str.toCharArray();
        chars[0]+=12;
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackage) {
        URL url=this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classDir=new File(url.getFile());
        for (File file:classDir.listFiles()){
            if (file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else {
                String className=(scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }
        }
        
    }

    private void doLoadConfig(String  contextConfigLocation) throws Exception{
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //内部类
    private class Handler{
        protected Object  controller;//保存方法对应的实例
        protected Method  method;//保存实例映射的方法
        protected Pattern pattern;
        protected Map<String,Integer> paramIndexMapping;//参数顺序

        public Handler(Object controller, Method method, Pattern pattern) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;
        }

        public Handler() {
        }
    }
}


