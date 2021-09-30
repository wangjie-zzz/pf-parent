package com.pf.gateway.authorization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        //预检请求
        if(HttpMethod.OPTIONS.matches(httpMethod)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return authentication
                        //过滤TOKEN解析验证成功的
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
                            List<Object> authUsers = new ArrayList<>();
                            Optional.ofNullable(jwtValue.getClaims().get("authorities")).ifPresent(authorities -> {
                                authUsers.addAll(Arrays.asList(((JSONArray) authorities).toArray()));
                            });
                            return authUsers;
                        })
                        //转成成权限名称
                        .any(roleId-> {
                            // 检测权限是否匹配
                            log.info("\r\n权限过滤，roleId: {}, url: {}, method: {}", roleId, url, httpMethod);
                            /* TODO
                            根据sessionId，在redis取出当前用户的所有角色rolesByRedis;
                            判断rolesByRedis是否包含当前roleId
                            根据当前roleId，在redis取出有哪些权限authByRedis
                            判断authByRedis是否包含当前url，httpMethod
                            认证完毕；
                            * */
                            return true;
                        })
                        .map( hasAuthority ->  new AuthorizationDecision(hasAuthority) )
                        .defaultIfEmpty(new AuthorizationDecision(true));
    }

}
