package com.j2ee.spring.spring_aop.example2_annotation;

import java.util.HashMap;
import java.util.Map;

public class AddressThreadVariable {

    /**
     * 代码转换线程变量存放的是key-value的键值对
     */
    private static ThreadLocal<Map<String, AdressAnnotation>> translateGroupVariable = new InheritableThreadLocal<Map<String, AdressAnnotation>>();


    public static Map<String, AdressAnnotation> getTranslateGroup() {
        if (translateGroupVariable.get() == null) {
            translateGroupVariable.set(new HashMap<>());
        }

        return translateGroupVariable.get();
    }

    public static void removePermissionDetailGroup() {
        translateGroupVariable.remove();
    }




    public static void removeAll() {
        translateGroupVariable.remove();
    }
}