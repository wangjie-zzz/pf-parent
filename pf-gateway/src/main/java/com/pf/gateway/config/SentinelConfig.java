package com.pf.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/***
* @Title:
* @Param:
* @description: 限流配置
*/
@Slf4j
@Configuration
public class SentinelConfig {

    // 自定义限流回退
    @Bean
    public BlockRequestHandler blockRequestHandler() {
        return new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                log.info(getClass().getName(), t);
                CommonResult restfulResponse = CommonResult.failed(SysStatusCode.SYS_BUSY);
                return ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(restfulResponse));
            }
        };
    }
}
