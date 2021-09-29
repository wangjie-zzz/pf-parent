package com.pf.plat.exchange.socket.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class JsonServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("注册事件");
    }
    
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("取消注册事件");
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有新客户端连接接入。。。"+ctx.channel().remoteAddress());
    }
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("失去连接");
    }
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the client.
		System.out.println(msg.toString());
		ctx.write("{\"accept\":\"true\"}");
		ReferenceCountUtil.release(msg);
//		ctx.writeAndFlush(msg.toString());
//		ctx.fireChannelActive();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        /*ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("执行成功="+future.isSuccess());
                }
            }
        });*/
    }
    /*public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered");
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged");
    }*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}
