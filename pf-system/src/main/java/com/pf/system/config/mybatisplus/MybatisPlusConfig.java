package com.pf.system.config.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : MybatisPlusConfig
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/19-19:51
 */
@MapperScan({"com.pf.system.dao"})
@Configuration
public class MybatisPlusConfig {
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }
}