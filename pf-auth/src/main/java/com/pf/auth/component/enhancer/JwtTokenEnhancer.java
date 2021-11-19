package com.pf.auth.component.enhancer;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * JWT内容增强器
 * 追加附加信息（不晓得适合放什么，refreshToken刷新后，会丢失）
 * Created by  on 2020/6/19.
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {

        Authentication authentication = oAuth2Authentication.getUserAuthentication();
        if( authentication != null ) {
            // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
    //            Object principal = authentication.getPrincipal();
    //            Map<String, Object> additionalInfo = Maps.newHashMap();
    //            additionalInfo.put("userName", authentication.getName());
    //            if( principal instanceof SecurityUser) {
    //                SecurityUser user = (SecurityUser) principal;
    //                Optional.ofNullable(user.getAuthorities()).ifPresent(authoritys -> {
    //                    additionalInfo.put("roles", 
    //                            authoritys.stream().map(authority -> authority.toString())
    //                                    .collect(Collectors.toList())
    //                    );
    //                });
    //            }
    //            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }
}
