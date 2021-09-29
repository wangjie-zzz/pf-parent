package com.pf.auth.component.handler;

import com.pf.model.SecurityUser;
import com.pf.util.CacheDataUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : MySavedRequestAwareAuthenticationSuccessHandler
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/23-14:25
 */
public class MySavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RedisTemplate redisTemplate;
    public MySavedRequestAwareAuthenticationSuccessHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        /*HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(AuthConstants.SessionAttr.PF_TARGET_URL) != null) {
            String url = session.getAttribute(AuthConstants.SessionAttr.PF_TARGET_URL).toString();
            // session缓存redis，saveRequest无法序列化（因此只保留了redirectUri,见MyFindByIndexNameSessionRepository.save方法）
            this.setDefaultTargetUrl(url);
        }*/
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        CacheDataUtil.setUser(request, redisTemplate, user);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
