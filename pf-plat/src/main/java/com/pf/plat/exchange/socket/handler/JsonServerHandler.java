package com.pf.plat.exchange.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class JsonServerHandler extends ChannelInboundHandlerAdapter {

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the client.
		System.out.println(msg.toString());
        ctx.write("{\"report_type\":\"01\",\"begin_date\":\"20191009\",\"serial_no\":\"201910096926\",\"record\":{\"timestmp\":\"20191009 11:21:11\",\"seq_no\":\"ZLSHNGC201910098500\",\"checker_code\":\"system\",\"result\":\"0\",\"remark\":null},\"end_date\":\"20191009\",\"fund_id\":\"hn2019\",\"bank_id\":\"5\",\"func_num\":\"101251\"}");
		ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}
