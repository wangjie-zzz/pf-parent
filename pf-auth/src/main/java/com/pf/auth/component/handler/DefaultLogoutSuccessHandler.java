package com.pf.auth.component.handler;

import com.pf.auth.constant.AuthConstants;
import com.pf.base.CommonResult;
import com.pf.util.JacksonsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: auth
 * @description:
 * @author: wj
 * @create: 2021-04-13 15:39
 **/
@Slf4j
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("logout: {}", authentication);
        Cookie accessTokenCookie = new Cookie(AuthConstants.Cookies.tokenKey, null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setDomain(AuthConstants.Cookies.domain);
        accessTokenCookie.setPath(AuthConstants.Cookies.path);
        httpServletResponse.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(AuthConstants.Cookies.refreshTokenKey, null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setDomain(AuthConstants.Cookies.domain);
        refreshTokenCookie.setPath(AuthConstants.Cookies.path);
        httpServletResponse.addCookie(refreshTokenCookie);

        Cookie userCookie = new Cookie(AuthConstants.Cookies.userKey, null);
        userCookie.setMaxAge(0);
        userCookie.setDomain(AuthConstants.Cookies.domain);
        userCookie.setPath(AuthConstants.Cookies.path);
        httpServletResponse.addCookie(userCookie);

        CommonResult restfulResponse = CommonResult.success("退出成功！");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(JacksonsUtils.writeValueAsString(restfulResponse));
    }
}
