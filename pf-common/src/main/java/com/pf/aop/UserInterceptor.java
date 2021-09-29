package com.pf.aop;

import com.pf.aop.context.UserContext;
import com.pf.constant.CommonConstants;
import com.pf.model.SecurityUser;
import com.pf.util.CacheDataUtil;
import com.pf.util.CookiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.session.MapSession;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Optional;

/**
 * @ClassName : UserInterceptor 
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/17-16:16
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    private RedisTemplate redisTemplate;
    
    public UserInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = CookiesUtils.getSessionId(request);
        if(!StringUtils.isEmpty(sessionId)) {
            sessionId = new String(Base64.getDecoder().decode(sessionId.getBytes()));
            SecurityUser user = (SecurityUser) redisTemplate.boundHashOps(CommonConstants.CACHE_KEY.USER).get(sessionId);
            log.info("redis获取用户缓存：{}, {}", sessionId, user);
            Optional.ofNullable(user).ifPresent(u ->
                    UserContext.setSysUserHolder(u.convertUserDto())
            );
        } else {
            log.warn("redis获取用户缓存：{}", sessionId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.clear();
    }
}
