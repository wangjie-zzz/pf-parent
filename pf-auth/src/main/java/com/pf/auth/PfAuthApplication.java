package com.pf.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.pf")
public class PfAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfAuthApplication.class, args);
    }

}
