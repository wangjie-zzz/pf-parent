package com.pf.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息
 * Created by  on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleDto {
    private String roleId;

    private String tenId;

    private String deptId;

    private String roleName;

    private String roleType;

}
