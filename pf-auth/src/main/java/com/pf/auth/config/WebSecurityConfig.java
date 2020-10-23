package com.pf.auth.config;

import com.pf.auth.component.DefaultUserDetailsService;
import com.pf.auth.component.MobileUserDetailsService;
import com.pf.auth.component.granter.MobileAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置
 * Created by  on 2020/6/19.
 */
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
}
