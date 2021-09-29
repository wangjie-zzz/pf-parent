package com.pf.auth.controller;

import com.pf.auth.constant.AuthConstants;
import com.pf.aop.context.UserContext;
import com.pf.model.Oauth2Token;
import com.pf.base.CommonResult;
import com.pf.enums.DataFormatsEnum;
import com.pf.util.HttpHeaderUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return CommonResult.success(token);
    }

    /**
     * @Title: token刷新
     * @Param:
     * @description:
     * @author: wangjie
     * @date: 2020/9/17 14:30
     * @return:
     * @throws:
     */
    @ApiOperation(value="token刷新", notes="token刷新")
    @ResponseBody
    @PostMapping(value = "/sso/refreshToken")
    public CommonResult refreshToken(@RequestBody Map<String, String> map) {
        UserContext.getSysUserHolder(true);
        
        HttpHeaders headers = HttpHeaderUtil.initHttpHeaders(DataFormatsEnum.JSON, DataFormatsEnum.JSON);
        final String clientStr = map.get("client_id") + ":" + map.get("client_secret");
        String base64ClientCredentials = new String(Base64.encodeBase64(clientStr.getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64ClientCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AuthConstants.TOKEN_URL)
                .queryParam("refresh_token", map.get("refresh_token"))
                .queryParam("grant_type", "refresh_token");
        ResponseEntity<Map> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                request,
                Map.class);
        Oauth2Token token = new Oauth2Token(response.getBody());
        
        return CommonResult.success(token);
    }
}
