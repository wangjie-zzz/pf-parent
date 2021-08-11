package com.pf.auth.component;

import com.pf.auth.constant.AuthConstants;
import com.pf.enums.LoginTypeEnum;
import com.pf.util.Asserts;
import com.pf.system.model.UserDto;
import com.pf.system.service.ISysUserInfoProvider;
import com.pf.auth.domain.SecurityUser;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.util.JacksonsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/***
* @Title:
* @Param:
* @description: 普通用户登录
* @return:
* @throws:
*/
@Slf4j
@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @DubboReference
    protected ISysUserInfoProvider iSysUserInfoProvider;

    /***
    * @Title: loadUserByUsername
    * @Param: [uniqueId]
    * @description:
    * @return: org.springframework.security.core.userdetails.UserDetails
    * @throws:
    */
    @Override
    public UserDetails loadUserByUsername(String uniqueId) {
        CommonResult<UserDto> commonResult = iSysUserInfoProvider.selectUserAndRoleInfo(Long.parseLong(uniqueId) ,getIdentityType());
        if(commonResult.getCode() != SysStatusCode.SUCCESS.getCode()) {
            Asserts.fail(commonResult.getMessage());
        }
        UserDto userDto = commonResult.getData();
        
        log.info("\r\nload user by username :{}", JacksonsUtils.writeValueAsString(userDto));

        return checkUserInfo(userDto);
    }
    private Integer getIdentityType() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String identityType = request.getParameter("identity_type");
        return StringUtils.isBlank(identityType) ? LoginTypeEnum.USER_CODE.getCode(): Integer.parseInt(identityType);
    }

    protected SecurityUser checkUserInfo(UserDto userDto) {
        if ( userDto==null ) {
            throw new UsernameNotFoundException(AuthConstants.USERNAME_PASSWORD_ERROR);
        }

        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(AuthConstants.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(AuthConstants.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(AuthConstants.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(AuthConstants.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
