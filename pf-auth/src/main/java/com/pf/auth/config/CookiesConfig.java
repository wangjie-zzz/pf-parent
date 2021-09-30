package com.pf.auth.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.session.web.http.CookieSerializer;

/**
 * @ClassName : NacosConfig
 * @Description :
 * @Author : wangjie
 * @Date: 2021/9/30-9:24
 */
@Configuration
public class CookiesConfig {

    @Bean
    public CookieSerializer setCookieSerializer() {
        return new MyCookieSerializer();
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        cookieSerializer.setSameSite(null);
//        return cookieSerializer;
    }
}
