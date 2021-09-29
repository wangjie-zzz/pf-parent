package com.pf.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * Created by  on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userTenId;
    private Long userId;
    private String userName;
    private String userPasswd;
    private Integer userUseState;
    private List<RoleDto> roles;

}
