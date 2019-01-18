package com.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class JacksonUtil {
    private static ObjectMapper objectMapper;

    public static <T> T toBeanFromStr(String jsonString,Class<T> c){
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        try {
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            T t=objectMapper.readValue(jsonString,c);
            return t;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转List<bean>
     * @param jsonString
     * @param c
     * @return
     */
    public static List toListFromStr(String jsonString, Class c){
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, c);
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            List t=objectMapper.readValue(jsonString,javaType);
            return t;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toStrFromBean(Object obj){
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        try {
            String jsonStr=objectMapper.writeValueAsString(obj);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 转json并output返回
     * @param obj
     * @param response
     */
    public static void toStrFromBean(Object obj,HttpServletResponse response)  {
        response.setContentType("text/html;charset=UTF-8");
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        try {
            objectMapper.writeValue(response.getOutputStream(), obj);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }


}