package com.pf.auth.component.handler;

import com.pf.util.CacheDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : UserCacheClearHandler
 * LogoutSuccessHandler已经获取不到sessionid，没法清除缓存
 * @Description :
 * @Author : wangjie
 * @Date: 2021/9/29-8:43
 */
@Slf4j
public class UserCacheClearHandler implements LogoutHandler {
    private RedisTemplate redisTemplate;
    public UserCacheClearHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        log.info("用户登出，清除redis缓存;");
        CacheDataUtil.removeUser(httpServletRequest, redisTemplate);
    }
}
