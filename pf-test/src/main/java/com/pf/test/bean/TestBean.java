package com.pf.test.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.spi.InitialContextFactory;

/**
 * @ClassName : TestBean
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/23-15:56
 */
@Data
@AllArgsConstructor
@Builder
public class TestBean implements DisposableBean, InitializingBean {
    private String name;
    public TestBean() {
        System.out.println("调用Bean的函数(constructor)");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        System.out.println("调用Bean的函数(setName/setAttribute)");
        this.name = name;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("调用Bean的函数(postConstruct)");
    }
    //MainConfig中@Bean 的initMethod
    public void initMethod(){
        System.out.println("调用Bean的函数(initMethod)");
    }
    //InitializingBean接口的方法afterPropertiesSet
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用Bean的函数(afterPropertiesSet)");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("调用Bean的函数(preDestroy)");
    }
    //DisposableBean接口的方法destroy
    @Override
    public void destroy() throws Exception {
        System.out.println("调用Bean的函数(destroy)");
    }
    //MainConfig中@Bean 的destroyMethod
    public void destroyMethod(){
        System.out.println("调用Bean的函数(destroyMethod)");
    }
}
