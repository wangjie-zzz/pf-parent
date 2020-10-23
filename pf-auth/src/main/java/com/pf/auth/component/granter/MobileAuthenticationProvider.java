package com.pf.auth.component.granter;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
* @Title:
* @Param:
* @description: 手机验证码登陆provider
* @return:
* @throws:
*/
public class MobileAuthenticationProvider extends DaoAuthenticationProvider {

    public MobileAuthenticationProvider() {}

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
