package com.pf.auth.config;

import com.pf.bean.BaseSwaggerConfig;
import com.pf.constant.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by  on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.pf.auth.controller")
                .title("pf认证中心")
                .description("pf认证中心相关接口文档")
                .contactName("pf")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
