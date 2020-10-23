package com.pf.system.config;

import com.pf.config.BaseSwaggerConfig;
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
                .apiBasePackage("com.pf.system.controller")
                .title("pf系统管理中心")
                .description("pf系统管理中心相关接口文档")
                .contactName("pf")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
