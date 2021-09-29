package com.pf.auth.config;

import com.google.common.collect.Lists;
import com.pf.auth.component.enhancer.JwtTokenEnhancer;
import com.pf.auth.component.exception.CustomAccessDeniedHandler;
import com.pf.auth.component.exception.CustomAuthenticationEntryPoint;
import com.pf.auth.component.exception.CustomWebResponseExceptionTranslator;
import com.pf.auth.component.granter.MobileTokenGranter;
import com.pf.auth.constant.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * 认证服务器配置
 *  * - 功能描述：
 *  * - 1、Spring-Security-OAuth2对于认证信息的存储提供了如下方案：数据库和内存。而此处将使用Mysql存储。
 *  * - 2、认证管理信息的配置主要是针对ClientDetails和UserDetails对象的检查，客户端模式针对ClientDetails检查，而密码模式则先检查ClientDetails后检查UserDetails对象。
 *  * <p>
 *  * /oauth/authorize：授权端点。
 *  * /oauth/token：令牌端点。
 *  * /oauth/confirm_access：用户确认授权提交端点。
 *  * /oauth/error：授权服务错误信息端点。
 *  * /oauth/check_token：用于资源服务访问的令牌解析端点。
 *  * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
 *  * {[/oauth/authorize]}
 *  * {[/oauth/authorize],methods=[POST]}
 *  * {[/oauth/token],methods=[GET]}
 *  * {[/oauth/token],methods=[POST]}
 *  * {[/oauth/check_token]}
 *  * {[/oauth/error]}
 * Created by  on 2020/6/19.
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("admin-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7)
                .and()
                .withClient("portal-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7);*/
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
        log.info("ClientDetailsServiceConfigurer is complete!");
    }

    /*
     * (非 Javadoc)
     * @Title: configure
     * @Description:
     * - 配置AuthorizationServer 端点的非安全属性，也就是 token 存储方式、token 配置、用户授权模式等。默认不需做任何配置，除非使用 密码授权方式, 这时候必须配置 AuthenticationManage
     * - 密码模式下配置认证管理器 AuthenticationManager,并且设置 AccessToken的存储介质tokenStore,如果不设置，则会默认使用内存当做存储介质。
     * - 而该AuthenticationManager将会注入 2个Bean对象用以检查(认证)
     * @param endpoints
     * @throws Exception
     * @modifyLog：修改令牌生成方式，新的令牌使用jwt
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()) // 指定token存储位置
                .authorizationCodeServices(authorizationCodeServices()) // 授权码模式持久化授权码code
                .approvalStore(approvalStore()) // 授权码模式code存储
                .authenticationManager(authenticationManager) // 指定认证管理器
                // .userDetailsService(userDetailsService) // 如果实现自定义DefaultTokenServices，则不需要UserDetailsService
                .exceptionTranslator(new CustomWebResponseExceptionTranslator()); // 自定义OAuth2异常处理


        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore()); // 指定token存储位置
        tokenServices.setSupportRefreshToken(true); // 支持令牌刷新
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService()); // 加载ClientDetails数据库相关配置项
        // 通过TokenEnhancerChain增强器链将jwtAccessTokenConverter(转换成jwt)和jwtTokenEnhancer(往里面加内容加信息)连起来
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = Lists.newArrayList();
        enhancerList.add(new JwtTokenEnhancer());
        enhancerList.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(enhancerList);

        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        endpoints.tokenServices(tokenServices);

        List<TokenGranter> granters = Lists.newArrayList(endpoints.getTokenGranter());
        granters.add(new MobileTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        endpoints.tokenGranter(new CompositeTokenGranter(granters));


        log.info("AuthorizationServerEndpointsConfigurer is complete.");
    }

    /*
     * (非 Javadoc)
     * @Title: configure
     * @Description:
     * - 用来配置令牌端点(Token Endpoint)的安全约束
     * - 声明安全约束，哪些允许访问，哪些不允许访问。配置 AuthorizationServer 的安全属性，也就是endpoint /oauth/token 。/oauth/authorize 则和其它用户 REST 一样保护。可以不配置
     * @param oauthServer
     * @throws Exception
     * @modifyLog：
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .allowFormAuthenticationForClients() // 允许客户表单认证,不加的话/oauth/token无法访问
                .checkTokenAccess("isAuthenticated()") // 开启/oauth/check_token验证端口认证权限访问
                .tokenKeyAccess("isAuthenticated()") // 开启/oauth/token_key验证端口无权限访问
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
        log.info("AuthorizationServerSecurityConfigurer is complete");
    }

    /***
     * @Title: tokenStore
     * @Param: [jwtAccessTokenConverter]
     * @description: 授权服务器token仓库
     * @return: org.springframework.security.oauth2.provider.token.TokenStore
     * @throws:
     */
    @Bean
    protected TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    /***
     * @Title: approvalStore
     * @Param: []
     * @description: 授权信息持久化实现
     * @return: ApprovalStore
     * @throws:
     */
    @Bean
    protected ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /***
     * @Title: authorizationCodeServices
     * @Param: []
     * @description: 授权码模式持久化授权码code
     * @return: org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
     * @throws:
     */
    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        // 授权码存储等处理方式类，使用jdbc，操作oauth_code表
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     *
     * @Title: authorizationJwtAccessTokenConverter
     * @Description: 授权服务器JwtAccessToken转换器
     * - 生成密钥库：keytool -genkeypair -alias jwt -keyalg RSA -keypass 123456 -validity 36500 -keystore jwt.jks -storepass 123456 -dname CN=j,OU=jj,O=jj,L=北京,ST=北京市,C=中国
     * - -genkeypair: 生成密钥对
     * - -alias: 产生别名,每个keystore都关联这一个独一无二的alias
     * - -keyalg: 指定密钥的算法（非对称加密）
     * - -keypass: 指定别名条目的密码(私钥的密码)
     * - -validity: 指定创建的证书有效期多少天
     * - -keystore: 密钥库的路径及名称，不指定的话，默认在操作系统的用户目录下生成一个".keystore"的文件
     * - -storepass: 指定密钥库的密码(获取keystore信息所需的密码)
     * - -dname: 指定证书拥有者信息,例如：  "CN=名字与姓氏,OU=组织单位名称,O=组织名称,L=城市或区域名称,ST=州或省份名称,C=单位的两字母国家代码"
     * - 生成公钥：keytool -list -rfc --keystore jwt-keystore.jks | openssl x509 -inform pem -pubkey
     * -- 需要安装openSSL,新建jwt-publickey.cer,将公钥信息复制到jwt-publickey.cer文件中并保存,公钥放在资源服务器中
     * - 导出公钥(可忽略该行)： keytool -export -alias jwt -keystore jwt.jks -file jwt-publickey.cer
     * @return JwtAccessTokenConverter
     * @modifyLog：
     */
    @Bean
    protected JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(AuthConstants.Jwt.KEYSTORE), AuthConstants.Jwt.KEYPASS.toCharArray());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(AuthConstants.Jwt.ALIAS));
        return jwtAccessTokenConverter;
    }

}
