package com.pf.auth.config;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @ClassName : DefaultDubboFallback
 * @Description :
 * @Author : wangjie
 * @Date: 2020/10/15-10:30
 */
@Slf4j
@Configuration
public class CustomDubboFallback {
    public CustomDubboFallback() {
    }

    @PostConstruct
    private void dubboProviderFallback() {
        DubboAdapterGlobalConfig.setProviderFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
                log.info(getClass().getName(), ex);
                CommonResult restfulResponse = CommonResult.failed(SysStatusCode.SERVICE_PROVIDER_SIDE_EXCEPTION);
                return AsyncRpcResult.newDefaultAsyncResult(restfulResponse, invocation);
            }
        });
    }

    @PostConstruct
    private void dubboConsumerFallback() {
        DubboAdapterGlobalConfig.setProviderFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
                log.info(getClass().getName(), ex);
                CommonResult restfulResponse = CommonResult.failed(SysStatusCode.SERVICE_CONSUMER_SIDE_EXCEPTION);
                return AsyncRpcResult.newDefaultAsyncResult(restfulResponse, invocation);
            }
        });
    }

}
