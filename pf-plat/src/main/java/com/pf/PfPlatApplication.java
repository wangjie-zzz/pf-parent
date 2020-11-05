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
@MapperScan({"com.pf.plat.dao"})
public class PfPlatApplication {
    public static void main(String[] args) {
        SpringApplication.run(PfPlatApplication.class);
    }
}
