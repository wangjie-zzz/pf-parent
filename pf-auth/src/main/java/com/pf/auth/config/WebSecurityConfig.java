package com.pf.auth.config;

import com.pf.auth.component.DefaultUserDetailsService;
import com.pf.auth.component.MobileUserDetailsService;
import com.pf.auth.component.exception.CustomAccessDeniedHandler;
import com.pf.auth.component.exception.CustomAuthenticationEntryPoint;
import com.pf.auth.component.granter.MobileAuthenticationProvider;
import com.pf.auth.constant.AuthConstants;
import com.pf.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsService defaultUserDetailsService;

    @Autowired
    private MobileUserDetailsService mobileUserDetailsService;

    /*
     * (非 Javadoc)
     * @Title: configure
     * @Description: web安全配置
     * @param web
     * @throws Exception
     * @modifyLog：
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favor.ico");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * (非 Javadoc)
     * @Title: configure
     * @Description: 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
     * @param auth
     * @throws Exception
     * @modifyLog：
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(defaultUserDetailsService)
                .passwordEncoder(passwordEncoder()); // 密码模式登陆的AuthenticationProvider
        authenticationManagerBuilder.authenticationProvider(mobileAuthenticationProvider()); // 设置手机验证码登陆的AuthenticationProvider
    }
    /***
     * @Title: mobileAuthenticationProvider
     * @Param: []
     * @description: 创建手机验证码登陆的AuthenticationProvider
     * @return: com.boot.auth.granter.MobileAuthenticationProvider
     * @throws:
     */
    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService); // 设置userDetailsService
        provider.setPasswordEncoder(passwordEncoder()); // 设置密码规则
        return provider;
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
                .antMatchers(CommonConstants.COMMON_PERMIT_ENDPOINT).permitAll()
                .antMatchers(AuthConstants.PERMIT_ENDPOINTS).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated();
        log.info("HttpSecurity is complete!");
    }
}
