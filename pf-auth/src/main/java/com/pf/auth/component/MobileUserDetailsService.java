package com.pf.auth.component;


import com.pf.util.Asserts;
import com.pf.model.UserDto;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/***
* @Title:
* @Param:
* @description: 手机验证码登陆, 用户相关获取
*/
@Slf4j
@Service
public class MobileUserDetailsService extends DefaultUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String uniqueId) {

        //TODO 短信验证码登录
        CommonResult<UserDto> commonResult = iSysUserInfoProvider.selectUserAndRoleInfo(Long.parseLong(uniqueId), null);
        if(commonResult.getCode() != SysStatusCode.SUCCESS.getCode()) {
            Asserts.fail(commonResult.getMessage());
        }
        UserDto userDto = commonResult.getData();
        // 如果为mobile模式，从短信服务中获取验证码（动态密码）
//        RestfulResponse<String, GeneralMeta>  credentialsRes = authService.getSmsCode(uniqueId, "LOGIN");
//        String credentials = credentialsRes.getData();
        log.info("\r\nload user by username :{}", userDto);

        return checkUserInfo(userDto);
    }
}
