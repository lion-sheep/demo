package com.example.schedulemanager.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/13 下午5:47
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public void checkApplicationContext() {
        if (null == ApplicationContextUtil.applicationContext) {
            new IllegalArgumentException("ApplicationContext 未注册");
        }
    }

    public ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return ApplicationContextUtil.applicationContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public void removeApplicationContext() {
        applicationContext = null;
    }
}
