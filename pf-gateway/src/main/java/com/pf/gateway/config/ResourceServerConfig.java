package com.pf.gateway.config;

import com.pf.gateway.exception.RestAuthenticationEntryPoint;
import com.pf.gateway.exception.RestfulAccessDeniedHandler;
import com.pf.gateway.authorization.CustomReactiveAuthorizationManager;
//import com..mall.filter.IgnoreUrlsRemoveJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 资源服务器配置
 * Created by  on 2020/6/19.
 */
@EnableConfigurationProperties(IgnoreUrlsConfig.class)
@EnableWebFluxSecurity
public class ResourceServerConfig {
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
//    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
//    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .cors().and().csrf().disable() //TODO 关闭跨域后也应关闭 待验证
                .authorizeExchange()
                .pathMatchers(ignoreUrlsConfig.getIgnoreds()).permitAll()//白名单配置
                .anyExchange().access(reactiveAuthorizationManager())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 匿名用户访问无权限资源时的异常
                .accessDeniedHandler(new RestfulAccessDeniedHandler()) // 认证过的用户访问无权限资源时的异常
                .and()
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(withDefaults())
                );
        return http.build();
    }
    @Bean
    public ReactiveAuthorizationManager<AuthorizationContext> reactiveAuthorizationManager(){
        ReactiveAuthorizationManager<AuthorizationContext> reactiveAuthorizationManager = new CustomReactiveAuthorizationManager();
        return reactiveAuthorizationManager;
    }

}
