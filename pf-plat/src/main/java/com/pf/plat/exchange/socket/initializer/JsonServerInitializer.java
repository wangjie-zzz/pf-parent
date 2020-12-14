package com.pf.plat.exchange.socket.initializer;

import com.pf.plat.exchange.socket.handler.JsonServerHandler;
import com.pf.plat.exchange.socket.handler.ServeIdleStateTrigger;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class JsonServerInitializer extends ChannelInitializer<SocketChannel> {

	private final SslContext sslContext;

    public JsonServerInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) {
    	ChannelPipeline channelPipeline = socketChannel.pipeline();
        if (sslContext != null) {
        	channelPipeline.addLast(sslContext.newHandler(socketChannel.alloc()));
        }
        //p.addLast(new LoggingHandler(LogLevel.INFO));
     
        channelPipeline.addLast(
                new IdleStateHandler(20, 0, 0, TimeUnit.SECONDS),
                new ServeIdleStateTrigger(),
        		new JsonObjectDecoder(),
        		new StringEncoder(),
        		new StringDecoder(),
        		new JsonServerHandler()
        );
    }
    
}
