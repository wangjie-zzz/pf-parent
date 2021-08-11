package com.pf.auth.controller;

import com.pf.auth.constant.AuthConstants;
import com.pf.auth.domain.Oauth2Token;
import com.pf.base.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: auth
 * @description:
 * @author: wj
 * @create: 2021-04-12 15:08
 **/
@Slf4j
@Controller
public class LoginController {
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sso/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @ResponseBody
    @PostMapping(value = "/oauth/callback")
    public CommonResult callback(@RequestBody Map<String, String> body, HttpServletRequest request, HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("client_id", body.get("client_id"));
        params.set("client_secret", body.get("client_secret"));
        params.set("code", body.get("code"));
        params.set("grant_type", "authorization_code");
        params.set("redirect_uri", body.get("redirect_uri"));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        
        ResponseEntity<Map> responseEntity = restTemplate.exchange(AuthConstants.TOKEN_URL, HttpMethod.POST, httpEntity, Map.class);
        log.info("\r\ntokenInfo : {}", responseEntity.getBody());
        assert responseEntity == null || responseEntity.getBody() == null;
        Oauth2Token token = new Oauth2Token(responseEntity.getBody());

//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(JacksonUtil.writeValueAsString(token));
        //将token放到cookie中
        Cookie accessTokenCookie = new Cookie(AuthConstants.Cookies.tokenKey, token.getAccessToken());
        accessTokenCookie.setMaxAge(token.getExpiresIn());
        accessTokenCookie.setDomain(AuthConstants.Cookies.domain);
        accessTokenCookie.setPath(AuthConstants.Cookies.path);
        response.addCookie(accessTokenCookie);
        Cookie refreshTokenCookie = new Cookie(AuthConstants.Cookies.refreshTokenKey, token.getRefreshToken());
        refreshTokenCookie.setMaxAge(AuthConstants.Cookies.refreshTokenInvalidTime);
        refreshTokenCookie.setDomain(AuthConstants.Cookies.domain);
        refreshTokenCookie.setPath(AuthConstants.Cookies.path);
        response.addCookie(refreshTokenCookie);

        Cookie userCookie = new Cookie(AuthConstants.Cookies.userKey, token.getJti());
        userCookie.setMaxAge(AuthConstants.Cookies.refreshTokenInvalidTime);
        userCookie.setDomain(AuthConstants.Cookies.domain);
        userCookie.setPath(AuthConstants.Cookies.path);
        response.addCookie(userCookie);
        
        return CommonResult.success();
    }
}
