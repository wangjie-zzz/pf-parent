package com.pf.model;

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
    private Long roleId;

    private Long tenId;

    private Long deptId;

    private String roleName;

    private Integer roleType;

}
