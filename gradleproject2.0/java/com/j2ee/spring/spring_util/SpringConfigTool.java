package com.j2ee.spring.spring_util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 *  动态获取spring bean对象
 */
@Repository
public class SpringConfigTool implements ApplicationContextAware {
    private static ApplicationContext context;
    private static SpringConfigTool stools = null;

    public SpringConfigTool() {
        System.out.println("实例化SpringConfigTool");
    }

    /**
     * 初始化springConfig
     * @return

    public SpringConfigTool init(){
        if(stools == null){
            stools = new SpringConfigTool();
        }
        return stools;
    }
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringConfigTool.context = context;
    }

    /**
     * 通过名称获取bean
     * @param beanName
     * @return
     */
    public synchronized static Object getBean(String beanName) {

        return context.getBean(beanName);
    }

}