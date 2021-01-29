package com.pf.plat.exchange.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName : ServeIdleStateTrigger
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/12/14-14:53
 */
public class ServeIdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.READER_IDLE) {
                throw new Exception("client is not exist!");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
