package com.pf.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName : SecurityGrantedAuthority
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/17-17:06
 */
public class SecurityGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 530L;
    private Long roleId;

    private Long tenId;

    private Long deptId;

    private String roleName;

    private Integer roleType;

    public SecurityGrantedAuthority(Long roleId, Long tenId, Long deptId, String roleName, Integer roleType) {
        this.roleId = roleId;
        this.tenId = tenId;
        this.deptId = deptId;
        this.roleName = roleName;
        this.roleType = roleType;
    }

    public SecurityGrantedAuthority(RoleDto roleDto) {
        this(roleDto.getRoleId(), roleDto.getTenId(),roleDto.getDeptId(), roleDto.getRoleName(), roleDto.getRoleType());
    }

    public String getAuthority() {
        return String.valueOf(this.roleId);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof SecurityGrantedAuthority ? 
                    this.roleId == ((SecurityGrantedAuthority)obj).roleId : false;
        }
    }

    public int hashCode() {
        return this.roleId.hashCode();
    }

    public String toString() {
        return this.roleId.toString();
    }
    
    public RoleDto convertRoleDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(this.roleId);
        roleDto.setDeptId(this.deptId);
        roleDto.setTenId(this.tenId);
        roleDto.setRoleName(this.roleName);
        roleDto.setRoleType(this.roleType);
        return roleDto;
    }
}
