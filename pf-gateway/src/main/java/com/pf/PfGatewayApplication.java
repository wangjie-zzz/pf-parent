package com.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PfGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfGatewayApplication.class, args);
    }

}
