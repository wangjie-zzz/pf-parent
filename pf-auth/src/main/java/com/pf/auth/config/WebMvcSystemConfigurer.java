package com.pf.auth.config;

import com.pf.aop.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName : SystemUserInterce
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/17-16:38
 */
@Configuration
public class WebMvcSystemConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(redisTemplate)).addPathPatterns("/**");
    }
}
