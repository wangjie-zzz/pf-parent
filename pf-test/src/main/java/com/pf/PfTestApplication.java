package com.pf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName : com.pf.test.PfTestApplication
 * @Description :
 * @Author : wangjie
 * @Date: 2020/10/23-15:53
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.pf.test.dao")
public class PfTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PfTestApplication.class);
    }
}
