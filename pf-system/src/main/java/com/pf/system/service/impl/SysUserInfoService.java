package com.pf.system.service.impl;

import com.google.common.collect.Lists;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.constant.CommonConstants;
import com.pf.enums.*;
import com.pf.system.constants.SystemConstants;
import com.pf.system.dao.SysUdeptRelMapper;
import com.pf.system.model.entity.SysUdeptRel;
import com.pf.util.Asserts;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.system.model.Token;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.model.request.LoginRequest;
import com.pf.system.service.ISysUserInfoService;
import com.pf.util.CacheDataUtil;
import com.pf.util.HttpHeaderUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
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
    @Autowired
    private SysUdeptRelMapper sysUdeptRelMapper;

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
        String userId = SnowflakeIdWorker.getInstance().nextIdString();
        sysUserInfo.setUserId(userId);
        sysUserInfo.setUserCode(sysUserInfo.getUserPhone());
        sysUserInfo.setUserIntUser(userId);
        sysUserInfo.setUserUpdUser(userId);
        sysUserInfo.setUserUpdDate(LocalDateTime.now());
        sysUserInfo.setUserIntDate(LocalDateTime.now());
        /*TODO 待添加默认公司部门等数据, sysUDeptRel待插入*/
        sysUserInfo.setUserPasswd(SystemConstants.DEFAULT_PWD);
        sysUserInfo.setUserUseState(UseStateEnum.EFFECTIVE.getCodeToStr());
        sysUserInfo.setUserDataSource(UserDataSourceEnum.WEB_REGISTER.getCodeToStr());
        sysUserInfoMapper.insert(sysUserInfo);
        return CommonResult.success();
    }
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
    public CommonResult<Object> adminCreate(SysUserInfo sysUserInfo) {
        SysUserInfo authUser = CacheDataUtil.getUserCacheBean(redisTemplate);
        sysUserInfo.setUserId(SnowflakeIdWorker.getInstance().nextIdString());
        sysUserInfo.setUserCode(sysUserInfo.getUserPhone());
        sysUserInfo.setUserPasswd(SystemConstants.DEFAULT_PWD);
        sysUserInfo.setUserIntUser(authUser.getUserId());
        sysUserInfo.setUserUpdUser(authUser.getUserId());
        sysUserInfo.setUserUpdDate(LocalDateTime.now());
        sysUserInfo.setUserIntDate(LocalDateTime.now());
        sysUserInfo.setUserUseState(UseStateEnum.EFFECTIVE.getCodeToStr());
        sysUserInfo.setUserDataSource(UserDataSourceEnum.ADMIN_CREATE.getCodeToStr());
        sysUserInfoMapper.insert(sysUserInfo);
        SysUdeptRel sysUdeptRel = new SysUdeptRel(sysUserInfo.getUserId(), sysUserInfo.getUserDeptId(), BoolEnum.TRUE.getCodeToStr());
        sysUdeptRelMapper.insert(sysUdeptRel);
        return CommonResult.success();
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
        HttpHeaders headers = HttpHeaderUtil.initHttpHeaders(DataFormatsEnum.JSON, DataFormatsEnum.JSON);
        String base64ClientCredentials = new String(Base64.encodeBase64(SystemConstants.CLIENT_CREDENTIALS.getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64ClientCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SystemConstants.AUTH_SERVER_URI)
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
        String sysUserInfoKey = CommonConstants.CACHE_KEY.SYS_USER_INFO_KEY_PREFIX + token.getJti();
        List<String> keys = Lists.newArrayList();
        keys.add(sysUserInfoKey);
        // 删除旧的缓存
        if( redisTemplate.hasKey(sysUserInfoKey) ) {
            redisTemplate.delete(keys);
        }
        // 保存新的缓存
        redisTemplate.opsForValue().set(sysUserInfoKey, token.getSysUserInfo(), CommonConstants.CACHE_KEY.EXPIRATION_TIME, TimeUnit.SECONDS);
        return CommonResult.success(token);
    }

    @Override
    public CommonResult<String> refreshToken(String refreshToken) {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo==null) Asserts.fail(SysStatusCode.UNAUTHORIZED);
        HttpHeaders headers = HttpHeaderUtil.initHttpHeaders(DataFormatsEnum.JSON, DataFormatsEnum.JSON);
        String base64ClientCredentials = new String(Base64.encodeBase64(SystemConstants.CLIENT_CREDENTIALS.getBytes()));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64ClientCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SystemConstants.AUTH_SERVER_URI)
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
