package com.pf.model;

import com.pf.enums.dicts.UseStateEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 * Created by  on 2020/6/19.
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private Long id = 111l;
    
    private Long userTenId;
    private Integer userUseState;
    /**
     * 用户名
     */
    private String username = "";
    /**
     * 用户密码
     */
    private String password = "41234";
    /**
     * 用户状态
     */
    private Boolean enabled = false;
//    /**
//     * 登录客户端ID
//     */
//    private String clientId;
    /**
     * 权限数据
     */
    private Collection<SecurityGrantedAuthority> authorities;

    public SecurityUser() {

    }

    public SecurityUser(UserDto userDto) {
        this.setId(userDto.getUserId());
        this.setUsername(userDto.getUserName());
        this.setPassword(userDto.getUserPasswd());
        this.setUserTenId(userDto.getUserTenId());
        this.setUserUseState(userDto.getUserUseState());
        this.setEnabled(UseStateEnum.EFFECTIVE.getCode() == userDto.getUserUseState());
        if (userDto.getRoles() != null) {
            authorities = new ArrayList<>();
            userDto.getRoles().forEach(item -> authorities.add(new SecurityGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public UserDto convertUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId(this.id);
        userDto.setUserPasswd(this.password);
        userDto.setUserName(this.username);
        userDto.setUserTenId(this.userTenId);
        userDto.setUserUseState(this.userUseState);
        userDto.setRoles(this.authorities.stream().map(authoritie -> authoritie.convertRoleDto()).collect(Collectors.toList()));
        return userDto;
    }
}
