package com.pf.test.config;

import com.pf.test.bean.TestBean;
import com.pf.test.bean.TestBeanProcessor;
import org.aspectj.weaver.ast.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName : TestBeanConfig
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/23-16:03
 */
/*
* @Component 注解并没有通过cglib来代理@Bean方法的调用，因此调用带@Bean注解的方法态返回的都是新的实例。
*
* ConfigurationClassPostProcessor，这个后置处理程序专门处理带有@Configuration注解的类，这个程序会在bean定义加载完成后，在bean初始化前进行处理。其主要处理的过程就是使用 cglib 动态代理对类进行增强，使用增强后的类替换了beanFactory原有的 beanClass，增强类会对其中带有@Bean注解的方法进行额外处理，确保调用带@Bean注解的方法返回的都是同一个实例*/
//@Component
@Configuration
public class TestBeanConfig {

    @Bean(initMethod = "initMethod",destroyMethod = "destroyMethod")
    public TestBean testBean() {
        return new TestBean();
    }

    @Bean
    public TestBeanProcessor testBeanProcessor() {
        return new TestBeanProcessor();
    }
}
