package com.pf.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 * Created by  on 2020/6/17.
 */
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    public String[] getIgnoreds() {
        return ignoreds;
    }

    public void setIgnoreds(String[] ignoreds) {
        this.ignoreds = ignoreds;
    }

    private String[] ignoreds;
}
