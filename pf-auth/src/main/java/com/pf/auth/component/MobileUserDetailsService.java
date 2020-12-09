package com.pf.auth.component;


import com.pf.util.Asserts;
import com.pf.system.model.UserDto;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.util.JacksonsUtils;
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

        CommonResult<String> commonResult = iSysUserInfoProvider.selectUserAndRoleInfo(uniqueId);
        if(commonResult.getCode() != SysStatusCode.SUCCESS.getCode()) {
            Asserts.fail(commonResult.getMessage());
        }
        String userDtoStr = commonResult.getData();
        // 如果为mobile模式，从短信服务中获取验证码（动态密码）
//        RestfulResponse<String, GeneralMeta>  credentialsRes = authService.getSmsCode(uniqueId, "LOGIN");
//        String credentials = credentialsRes.getData();
        UserDto userDto = JacksonsUtils.readValue(userDtoStr ,UserDto.class);
        log.info("\r\nload user by username :{}", userDto);

        return checkUserInfo(userDto);
    }
}
