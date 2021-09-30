package com.pf.auth.model.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Oauth2TokenVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String refreshToken;
    private String jti;

    public Oauth2TokenVo(Map<String, Object> map) {
        this.accessToken = (String) map.get("access_token");
        this.refreshToken = (String) map.get("refresh_token");
        this.jti = (String) map.get("jti");
    }
}
