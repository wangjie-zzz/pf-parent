package com.pf.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户信息
 * Created by  on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userName;
    private String userPasswd;
    private String userUseState;
    private List<RoleDto> roles;

}
