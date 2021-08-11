package com.pf.auth.component.handler;

import com.pf.base.CommonResult;
import com.pf.util.JacksonsUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: auth
 * @description:
 * @author: wj
 * @create: 2021-04-13 15:39
 **/
public class DefaultLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        CommonResult restfulResponse = CommonResult.failed(e.getMessage());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(JacksonsUtils.writeValueAsString(restfulResponse));
    }
}
