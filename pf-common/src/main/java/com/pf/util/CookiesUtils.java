package com.pf.util;

import com.pf.constant.CommonConstants;
import com.pf.model.Oauth2Token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * @ClassName : CookiesUtils
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/11-13:34
 */
public class CookiesUtils {
    private static final String SESSION_KEY = "SESSION";

    public static String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Optional<Cookie> optional = Arrays.stream(cookies).filter(cookie -> SESSION_KEY.equals(cookie.getName())).findFirst();
            if(optional.isPresent()){
                return optional.get().getValue();
            }
        }
        return "";
    }
}
