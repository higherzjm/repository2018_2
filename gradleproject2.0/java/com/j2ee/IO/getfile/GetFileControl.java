package com.j2ee.IO.getfile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zjm on 2018/11/23.
 */
@Controller
@RequestMapping("/getfilecontrol")
public class GetFileControl {

    /**
     * http://localhost:8080/repository2018_2/getfilecontrol/getWEB-INFFilePath.do
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getWEB-INFFilePath")
    @ResponseBody
    public String getWEB_INFFilePath(HttpServletRequest request, HttpServletResponse response){
        String contextPath1="",contextPath2="",contextPath3="";
        String s="";
        try {
             contextPath1=request.getSession().getServletContext().getContextPath();
             contextPath2=request.getServletPath();
             contextPath3=request.getContextPath();
             System.out.println("contextPath1:"+contextPath1+";contextPath2:"+contextPath2+";contextPath3:"+contextPath3);

             InputStream inputStream=request.getServletContext().getResourceAsStream("/WEB-INF/temporaryfile/readMe.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             String str = "";
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                s=s+str;
            }
            inputStream.close();// 关闭流
        }catch (Exception e){
            e.printStackTrace();
        }
       return s ;
    }

    @RequestMapping("/getResourcesFilePath")
    @ResponseBody
    public String getResourcesFilePath(HttpServletRequest request, HttpServletResponse response){
        String contextPath1="",contextPath2="",contextPath3="";
        String s="";
        try {
            contextPath1=request.getSession().getServletContext().getContextPath();
            contextPath2=request.getServletPath();
            contextPath3=request.getContextPath();
            System.out.println("contextPath1:"+contextPath1+";contextPath2:"+contextPath2+";contextPath3:"+contextPath3);

            InputStream inputStream=request.getServletContext().getResourceAsStream("/mytxt.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str = "";
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                s=s+str;
            }
            inputStream.close();// 关闭流
        }catch (Exception e){
            e.printStackTrace();
        }
        return  s;
    }
}
