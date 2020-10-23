package com.pf.auth.controller;

import com.pf.auth.constant.JwtConsts;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 获取RSA公钥接口
 * Created by  on 2020/6/19.
 */
@RestController
public class KeyPairController {

    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(JwtConsts.KEYSTORE), JwtConsts.KEYPASS.toCharArray());
        RSAPublicKey publicKey = (RSAPublicKey) keyStoreKeyFactory.getKeyPair(JwtConsts.ALIAS).getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
