package com.pf.plat.exchange.socket.handler;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : ReconnectHandler
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-9:48
 */
@Slf4j
public class PingHandler extends ChannelInboundHandlerAdapter {


    private String heartbeatRequest;
    private String heartbeatResponse;
    private Random random = new Random();
    private int baseRandom = 10;

    private Channel channel;

    public PingHandler(String heartbeatRequest, String heartbeatResponse){
        this.heartbeatRequest = heartbeatRequest;
        this.heartbeatResponse = heartbeatResponse;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        ping(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if( StringUtils.isNotBlank(heartbeatResponse) && msg.equals(heartbeatResponse) ) {
            /*log.info("\r\n{}","receive a heartbeat response and prepare to discard it.");*/
            ReferenceCountUtil.release(msg);
        } else {
            super.channelRead(ctx, msg);
        }
    }

    private void ping(Channel channel) {
        /*int second = Math.max(1, random.nextInt(baseRandom));*/
        int second = this.baseRandom;
        /*log.info("\r\n{}","next heart beat will send after " + second + "s.");*/
        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                if (channel.isActive()) {
                    /*log.info("\r\n{}","sending heart beat to the server...");*/
                    channel.writeAndFlush(heartbeatRequest);
                } else {
                    log.info("\r\n{}","The connection had broken, cancel the task that will send a heart beat.");
                    channel.closeFuture();
                    throw new RuntimeException();
                }
            }
        }, second, TimeUnit.SECONDS);

        future.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
