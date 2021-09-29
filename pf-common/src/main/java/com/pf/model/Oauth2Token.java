package com.pf.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class Oauth2Token implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;
    private String scope;
    private String jti;
    private List<String> roles;
    private String username;
    private String time;

    public Oauth2Token(Map<String, Object> map) {
        this.accessToken = (String) map.get("access_token");
        this.tokenType = (String) map.get("token_type");
        this.refreshToken = (String) map.get("refresh_token");
        this.expiresIn = (int) map.get("expires_in");
        this.scope = (String) map.get("scope");
        this.jti = (String) map.get("jti");
        this.username = (String) map.get("userName");
        this.time = String.valueOf(map.get("time"));

        this.roles = (List<String>) map.get("roles");
    }
}
