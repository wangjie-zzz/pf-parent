package com.pf.test.bean;

import com.pf.test.bean.TestBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @ClassName : TestBeanConfig
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/23-16:01
 */
public class TestBeanProcessor implements BeanPostProcessor, BeanFactoryPostProcessor {
//    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass()== TestBean.class){
            System.out.println("调用postProcessBeforeInitialization...");
        }
        return bean;
    }
//    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass()==TestBean.class){
            System.out.println("调用postProcessAfterInitialization...");
        }
        return bean;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
