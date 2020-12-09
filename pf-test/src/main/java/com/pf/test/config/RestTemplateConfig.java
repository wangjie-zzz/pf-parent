package com.pf.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/***
* @Title:
* @Param:
* @description: rest客户端
* @return:
* @throws:
*/
@Configuration
public class RestTemplateConfig {

    RestTemplateBuilder builder;

    @Autowired
    private void setBuilder(RestTemplateBuilder builder){
        // 如果是StringHttpMessageConverter,则修改器默认字符集:ISO-8859-1为utf-8
        // postForObject 底层均采用了MappingJackson2HttpMessageConverter来处理请求
        // String格式提交数据时，底层其实采用的是StringHttpMessageConverter来处理请求
        List<HttpMessageConverter<?>> converters = builder.build().getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            if(httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName("UTF-8"));
                break;
            }
        }
        this.builder = builder;
    }

	@Bean( name="loadBalancedRestTemplate" )
	@LoadBalanced
	public RestTemplate loadBalancedRestTemplate() {
        return builder.build();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}
}
