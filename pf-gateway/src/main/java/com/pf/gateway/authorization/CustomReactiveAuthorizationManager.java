package com.pf.gateway.authorization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/***
* @Title:
* @Param:
* @description: 鉴权管理器，用于判断是否有资源的访问权限
* @return:
* @throws:
*/
@Slf4j
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        //获取请求
        ServerHttpRequest request =  authorizationContext.getExchange().getRequest();
        //判断当前是否有接口权限
        String url =request.getPath().value();
        log.info("\r\n请求url:{}",url);
        String httpMethod = request.getMethod().name();
        log.info("\r\n请求方法:{}",httpMethod);
        //需要进行权限验证的
        return authentication
                        //过滤验证成功的
                        .filter(a ->  a.isAuthenticated())
                        //转换成Flux
                        .flatMapIterable(a -> {
                            Jwt jwtValue = null;
                            if(a.getPrincipal() instanceof Jwt){
                                jwtValue = (Jwt)a.getPrincipal();
                            }
                            log.info("\r\nHeader头部:{}", jwtValue.getHeaders());
                            log.info("\r\nPayload负载:{}", jwtValue.getClaims());
                            log.info("\r\nSignature 签名:{}", jwtValue.getTokenValue());
                            log.info("\r\ngetAuthorities:{}", a.getAuthorities());
                            log.info("\r\n((Jwt)a.getDetails()):{}", a.getDetails());
                            //存储当前数据
                            List<Object> authUsers = new ArrayList<>();
                            /*TODO token不存储附加信息
                            *  当refreshToken刷新后，附加信息同时会清空
                            *  先将权限等控制放入自定义逻辑中*/
//                            authUsers.add(jwtValue.getClaims().get("roles"));
                            authUsers.add(new ArrayList<>());
                            return authUsers;
                        })
                        //转成成权限名称
                        .any(c-> {
                            // 检测权限是否匹配
                            log.info("\r\n权限过滤:{}:", c);
                            //获取当前用户
                            /*BaseUser baseUser = c.getBaseUser();
                            //判断当前携带的Token是否有效
                            String  token = request.getHeaders().getFirst(Constant.AUTHORIZATION).replace("Bearer ","");
                            if(!TokenUtil.judgeTokenValid(String.valueOf(baseUser.getId()),redisTemplate,token)){
                                return false;
                            }
                            //获取当前权限
                            String authority = c.getAuthority();
                            //通过当前权限码查询可以请求的地址
                            log.debug("当前权限是：{}",authority);
                            List<Permission> permissions = permissionUtil.getResultPermission(authority);
                            permissions = permissions.stream().filter(permission -> StringUtils.isNotBlank(permission.getRequestUrl())).collect(Collectors.toList());
                            //请求URl匹配，放行
                            if(permissions.stream().anyMatch(permission -> matcher.match(permission.getRequestUrl(),url))){
                                return true;
                            }*/
                            return true;
                        })
                        .map( hasAuthority ->  new AuthorizationDecision(hasAuthority) )
                        .defaultIfEmpty(new AuthorizationDecision(true));
    }

    /**
     * 构造对象
     */
    @Data
    class AuthUser{
        private String authority;

        /*private BaseUser baseUser;*/
    }

}
