package com.pf.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录用户信息
 * Created by  on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long tenId;

    private Long deptId;

    private String roleName;

    private Integer roleType;

}
