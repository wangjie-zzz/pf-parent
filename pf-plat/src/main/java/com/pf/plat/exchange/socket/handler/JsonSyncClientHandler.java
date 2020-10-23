package com.pf.plat.exchange.socket.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName : ReconnectHandler
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-9:48
 */
@Slf4j
public class JsonSyncClientHandler extends SimpleChannelInboundHandler<String> {

    private final BlockingQueue<String> answer = new LinkedBlockingQueue<String>();

    public String getResponse() {
        boolean interrupted = false;
        try {
            for (;;) {
                try {
                    return answer.take();
                } catch (InterruptedException ignore) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        /*log.info("Channel注册到EventLoop上，可以处理I/O");*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        /*log.info("Channel是连接/绑定就绪");*/
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        ctx.channel().close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                log.info("从Channel中读数据,{}",msg);
                boolean offered = answer.offer(msg);
                assert offered;
            }
        });
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("读操作完成，关闭Channel");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.info("Channel异常");
        cause.printStackTrace();
        ctx.close();
    }

}
