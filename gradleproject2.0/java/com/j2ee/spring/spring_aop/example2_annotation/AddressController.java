package com.j2ee.spring.spring_aop.example2_annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zjm on 2019/1/6.
 */
@Controller
@RequestMapping("addresscontroller")
public class AddressController {
    @RequestMapping("getaddress")
    @AdressAnnotation(province ="福建省",city = "龙岩市",isforeign = false,countyNum = 30)
    @ResponseBody
    public String getAddress(HttpServletRequest request, HttpServletResponse response){
        System.out.println("示例2控制器方法");
        return "调用成功";
    }
}
