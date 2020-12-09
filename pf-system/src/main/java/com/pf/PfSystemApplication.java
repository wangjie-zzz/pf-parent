package com.pf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName : MallSystemApplication
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/15-15:34
 */
//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.pf")
@MapperScan({"com.pf.system.dao"})
public class PfSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PfSystemApplication.class);
    }
}
