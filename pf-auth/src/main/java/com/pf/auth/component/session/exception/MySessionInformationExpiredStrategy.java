package com.pf.auth.component.session.exception;

import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.util.CacheDataUtil;
import com.pf.util.JacksonsUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : session过期处理：MySessionInformationExpiredStrategy
 *         SimpleRedirectSessionInformationExpiredStrategy
 *         ResponseBodySessionInformationExpiredStrategy
 * 
 * session过期场景：
 * 1.同一用户的session超过maximumSessions（前提maxSessionsPreventsLogin=false,即session数超出后不抛出异常）
 * 2.xxx
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/11-17:45
 */
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    private RedisTemplate redisTemplate;
    public MySessionInformationExpiredStrategy(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /*
    * concurrentSessionFilter
    * 判断session是否过期，过期则进入
    */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        
        CacheDataUtil.removeUser(event.getRequest(), redisTemplate);
        HttpServletResponse response = event.getResponse();
        CommonResult restfulResponse = CommonResult.failed(SysStatusCode.FORCE_LOGOUT);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JacksonsUtils.writeValueAsString(restfulResponse));
//        response.getWriter().print("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
//        response.flushBuffer();
    }
}
