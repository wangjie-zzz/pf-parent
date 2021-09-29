package com.pf;

import com.pf.constant.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.pf")
@EnableRedisHttpSession(redisNamespace = CommonConstants.CACHE_KEY.SESSION)
public class PfAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfAuthApplication.class, args);
    }

}
