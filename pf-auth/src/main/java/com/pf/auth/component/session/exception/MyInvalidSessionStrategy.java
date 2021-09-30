package com.pf.auth.component.session.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : MyInvalidSessionStrategy-session非法（或失效）
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/11-17:43
 */
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
//    private final String destinationUrl;
//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    public MyInvalidSessionStrategy(/*String invalidSessionUrl*/) {
//        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
//        this.destinationUrl = invalidSessionUrl;
    }
    /*
     * sessionid不存在进入
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.logger.debug("Starting new session (if required) and redirecting to none");
//        this.logger.debug("Starting new session (if required) and redirecting to '" + this.destinationUrl + "'");
        if (this.createNewSession) {
            request.getSession();
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 后端不做重定向
//        this.redirectStrategy.sendRedirect(request, response, this.destinationUrl);
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

}
