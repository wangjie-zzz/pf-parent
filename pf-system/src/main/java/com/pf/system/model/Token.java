package com.pf.system.model;

import com.pf.system.model.entity.SysUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : Token
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-16:31
 */
@Data
public class Token {

    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokentype;

    @ApiModelProperty(value = "刷新类型")
    private String refreshToken;

    @ApiModelProperty(value = "有效时间")
    private Integer expiresIn;

    @ApiModelProperty(value = "权限范围")
    private String scope;

    @ApiModelProperty(value = "用户角色")
    private List<String> roles;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "唯一身份标识")
    private String jti;

    @ApiModelProperty(value = "令牌创建时间")
    private String time;

    private SysUserInfo sysUserInfo;

    public Token (Map<String, Object> map , SysUserInfo sysUserInfo) {
        this.accessToken = String.valueOf(map.get("access_token"));
        this.tokentype = String.valueOf(map.get("token_type"));
        this.refreshToken = String.valueOf(map.get("refresh_token"));
        this.expiresIn = Integer.valueOf(String.valueOf(map.get("expires_in")));
        this.scope = String.valueOf(map.get("scope"));
        this.roles = (List<String>) map.get("roles");
        this.userName = String.valueOf(map.get("userName"));
        this.jti = String.valueOf(map.get("jti"));
        this.time = String.valueOf(map.get("time"));
        this.sysUserInfo = sysUserInfo;
        this.sysUserInfo.setUserPasswd(null);
    }
}
