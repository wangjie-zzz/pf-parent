package com.pf.auth.config;

import com.pf.auth.component.exception.CustomAccessDeniedHandler;
import com.pf.auth.component.exception.CustomAuthenticationEntryPoint;
import com.pf.constant.PermitEndpointConsts;
import com.pf.constant.ResourcesIdConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsUtils;

/**
 * @功能描述：资源服务配置
 * @修改日志：
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources
                .resourceId(ResourcesIdConsts.MALL_AUTH)
                .stateless(true)
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 匿名用户访问无权限资源时的异常
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // 认证过的用户访问无权限资源时的异常
        ;
        log.info("ResourceServerSecurityConfigurer is complete!");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 匿名用户访问无权限资源时的异常
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // 认证过的用户访问无权限资源时的异常
                .and()
                .authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(PermitEndpointConsts.COMMON).permitAll()
                .antMatchers(PermitEndpointConsts.AUTH).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated();
        log.info("HttpSecurity is complete!");
    }
}
