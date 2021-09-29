package com.pf.aop.context;

import com.pf.enums.SysStatusCode;
import com.pf.model.UserDto;
import com.pf.util.Asserts;
import org.springframework.core.NamedThreadLocal;

/**
 * @ClassName : UserContext
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/17-16:25
 */
public class UserContext {
    private static final ThreadLocal<UserDto> sysUserHolder = new NamedThreadLocal("current sys_user");

    public UserContext() {
    }

    public static void clear() {
        sysUserHolder.remove();
    }

    public static void setSysUserHolder(UserDto user) {
        sysUserHolder.set(user);
    }

    public static UserDto getSysUserHolder() {
        return getSysUserHolder(false);
    }
    public static UserDto getSysUserHolder(boolean check) {
        UserDto dto =  sysUserHolder.get();
        if(dto == null && check) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        return dto;
    }
}
