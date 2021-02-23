package com.pf.system.service.impl;

import com.google.common.collect.Lists;
import com.pf.base.CommonResult;
import com.pf.constant.AuthConstant;
import com.pf.constant.CacheConstants;
import com.pf.enums.LoginTypeEnum;
import com.pf.enums.SysStatusCode;
import com.pf.enums.UserDataSourceEnum;
import com.pf.util.Asserts;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.system.model.domain.Token;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.model.request.LoginRequest;
import com.pf.system.service.ISysUserInfoService;
import com.pf.util.CacheDataUtil;
import com.pf.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysUserInfoService implements ISysUserInfoService {

    @Autowired
    private RestTemplate loadBalancedRestTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    /**
    * @Title: 用户注册
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:38
    * @return:
    * @throws:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> registerGuest(SysUserInfo sysUserInfo) {
        SysUserInfo.updateDefaultInfo(sysUserInfo);
        sysUserInfo.setUserDataSource(UserDataSourceEnum.WEB_REGISTER.getCodeToStr());
        sysUserInfoMapper.insert(sysUserInfo);
        return CommonResult.success(null);
    }

    /**
    * @Title: login
    * @Param: [type, sysUserInfo]
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 16:39
    * @throws:
    */
    @Override
    @Transactional(readOnly = true)
    public CommonResult<Token> login(LoginRequest loginRequest) {
        String type = loginRequest.getType();
        String code = loginRequest.getCode();
        if(!LoginTypeEnum.USER_CODE.getCodeToStr().equals(type) && !LoginTypeEnum.PHONE.getCodeToStr().equals(type)) {
            Asserts.fail("登录类型错误！");
        }
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectUserAndPostInfo(type, code);
        sysUserInfo.checkUserUseState();
        HttpEntity<String> request = new HttpEntity<String>(HttpHeaderUtil.createLoginHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AuthConstant.AUTH_SERVER_URI)
                .queryParam("username", sysUserInfo.getUserId())
                .queryParam("password", loginRequest.getPwd())
                .queryParam("grant_type", "password");
        ResponseEntity<Map> response = loadBalancedRestTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                request,
                Map.class);
        Map<String, Object> map = response.getBody();
        if( map.get("access_token") == null ) {
            return CommonResult.failed(SysStatusCode.USER_CODE_PWD_FAIL);
        }
        // 提取令牌信息
        Token token = new Token(map, sysUserInfo);
        // 缓存处理
        String sysUserInfoKey = CacheConstants.SYS_USER_INFO_KEY_PREFIX + token.getJti();
        List<String> keys = Lists.newArrayList();
        keys.add(sysUserInfoKey);
        // 删除旧的缓存
        if( redisTemplate.hasKey(sysUserInfoKey) ) {
            redisTemplate.delete(keys);
        }
        // 保存新的缓存
        redisTemplate.opsForValue().set(sysUserInfoKey, token.getSysUserInfo(), CacheConstants.EXPIRATION_TIME, TimeUnit.SECONDS);
        return CommonResult.success(token);
    }

    @Override
    public CommonResult<String> refreshToken(String refreshToken) {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo==null) Asserts.fail(SysStatusCode.UNAUTHORIZED);
        HttpEntity<String> request = new HttpEntity<String>(HttpHeaderUtil.createLoginHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AuthConstant.AUTH_SERVER_URI)
                .queryParam("refresh_token", refreshToken)
                .queryParam("grant_type", "refresh_token");
        ResponseEntity<Map> response = loadBalancedRestTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                request,
                Map.class);
        Map<String, Object> map = response.getBody();
        if( map.get("access_token") == null ) {
            return CommonResult.failed(SysStatusCode.USER_CODE_PWD_FAIL);
        }
        return CommonResult.success(map.get("access_token").toString());
    }
}
