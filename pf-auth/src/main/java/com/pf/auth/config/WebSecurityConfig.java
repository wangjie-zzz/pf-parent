package com.pf.auth.config;

import com.pf.auth.component.DefaultUserDetailsService;
import com.pf.auth.component.MobileUserDetailsService;
import com.pf.auth.component.granter.MobileAuthenticationProvider;
import com.pf.auth.component.handler.MyLoginFailureHandler;
import com.pf.auth.component.handler.MyLogoutSuccessHandler;
import com.pf.auth.component.handler.MySavedRequestAwareAuthenticationSuccessHandler;
import com.pf.auth.component.handler.UserCacheClearHandler;
import com.pf.auth.component.session.exception.MyInvalidSessionStrategy;
import com.pf.auth.component.session.exception.MySessionInformationExpiredStrategy;
import com.pf.auth.constant.AuthConstants;
import com.pf.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsUtils;


@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsService defaultUserDetailsService;

    @Autowired
    private MobileUserDetailsService mobileUserDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;

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

    /*@Bean
    public RedisIndexedSessionRepository sessionRepository() {
        return new RedisIndexedSessionRepository(sessionRedisTemplate);
    }*/

    /*@Bean
    public MyFindByIndexNameSessionRepository sessionRepository() {
        return new MyFindByIndexNameSessionRepository(sessionRedisTemplate);
    }*/
    /*@Bean
    public SessionRepository sessionRepository() {
        return new MapSessionRepository(new HashMap<>());
    }*/
    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;
    @Bean
    public SessionRegistry sessionRegistry() {
//        return new MySessionRegistryImpl(sessionRepository());
//        return new DefaultSessionRegistryImpl();
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http接口保护*/
        http.httpBasic()// 客户端凭证校验 Basic
                .and().cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers(CommonConstants.COMMON_PERMIT_ENDPOINT).permitAll()
                .antMatchers(AuthConstants.PERMIT_ENDPOINTS).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated();
        /*http登录处理*/
        http.formLogin().loginPage(AuthConstants.LOGIN_PAGE).loginProcessingUrl(AuthConstants.LOGIN_PROCESS_URL).permitAll()
                .successHandler(new MySavedRequestAwareAuthenticationSuccessHandler(redisTemplate))
                .failureHandler(new MyLoginFailureHandler())
                .and().logout()
                .addLogoutHandler(new UserCacheClearHandler(redisTemplate))
                .logoutSuccessHandler(new MyLogoutSuccessHandler());
        /*Session*/
        /* TODO 需要确认的是：@EnableWebFluxSecurity资源服务器对业务接口api的校验：
            是否只校验(springsecurity-oauth的校验)token，
            还会校验(springsecurity的校验)session状态(过期或失效)吗？ */
        http.sessionManagement()
                // TODO 问题：前端使用token访问资源，那么cookies的session信息将不会得到实时的校验
                // session非法
                .invalidSessionStrategy(new MyInvalidSessionStrategy())
                // session并发控制：session过期，最大数，及超出最大数的处理策略等
                .maximumSessions(1).maxSessionsPreventsLogin(false).sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(new MySessionInformationExpiredStrategy(redisTemplate));
        
        log.info("HttpSecurity is complete!");
    }

}
