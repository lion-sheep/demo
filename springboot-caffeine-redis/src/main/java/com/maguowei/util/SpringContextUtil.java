package com.maguowei.util;/**
 * @author maguowei
 * @date ${DTAE} 下午5:01
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author maguowei
 * @date 2018/4/13 下午5:01
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    //获取应用上下文，并放入jvm的方法区常量池中
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public void checkApplicationContext(){
        if(null == applicationContext){
            new IllegalArgumentException("ApplicationContext 未注册");
        }
    }

    public ApplicationContext getApplicationContext(){
        checkApplicationContext();
        return applicationContext;
    }

    public void removeApplicationContext(){
        applicationContext = null;
    }

    public <T> T getBean(String name){
       return (T)applicationContext.getBean(name);
    }

    public <T> T getBean(Class<T> clazz){
        return (T)applicationContext.getBean(clazz);
    }
}
