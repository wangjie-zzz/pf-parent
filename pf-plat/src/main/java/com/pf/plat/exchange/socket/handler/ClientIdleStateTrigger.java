package com.pf.plat.exchange.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : ServeIdleStateTrigger
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/12/14-14:53
 */
@Slf4j
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {
    private String heartbeatRequest;
    public ClientIdleStateTrigger(String heartbeatRequest) {
        this.heartbeatRequest = heartbeatRequest;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.WRITER_IDLE) {
                log.info("send heartbeat to server!");
                ctx.writeAndFlush(heartbeatRequest);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
