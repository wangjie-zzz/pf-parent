package com.pf.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
    @Override
    public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
        applicationContext=applicationContextParam;
    }
    
    public static Object getObject(String id) {
        return applicationContext.getBean(id);
    }
    public static <T> T getObject(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static Object getBean(String tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static String getActiveProfile() {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if( activeProfiles != null && activeProfiles.length > 0 ) {
            return activeProfiles[0];
        }
        return "";
    }
}
