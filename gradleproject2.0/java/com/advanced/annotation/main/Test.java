package com.advanced.annotation.main;
 
import com.advanced.annotation.entity.BlackPeople;
import com.advanced.annotation.util.AnnotationUtil;
import com.advanced.annotation.util.ClassUtil;

import java.util.List;
 


public class Test {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// 获取特定包下所有的类(包括接口和类)
		List<Class<?>> clsList = ClassUtil.getAllClassByPackageName(BlackPeople.class.getPackage());
		//输出所有使用了特定注解的类的注解值
		AnnotationUtil.validAnnotation(clsList);
	}
 
}
