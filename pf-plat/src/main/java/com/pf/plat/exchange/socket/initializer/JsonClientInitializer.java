package com.pf.plat.exchange.socket.initializer;

import com.pf.plat.exchange.socket.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : JsonClientInitializer
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-9:45
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
public class JsonClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;

    private final String heartbeatRequest;

    private final String heartbeatResponse;

    private final ReconnectHandler reconnectHandler;


    @Builder.Default
    private Charset charset = Charset.defaultCharset();

    private String initResData;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        if(sslContext != null) {
            pipeline.addLast(sslContext.newHandler(socketChannel.alloc()));
        }

        if(reconnectHandler != null) {
            pipeline.addLast(reconnectHandler);
        }
        pipeline.addLast(
                new JsonObjectDecoder(),
                new StringDecoder(charset),
                new StringEncoder(charset)
        );
        if(!StringUtils.isEmpty(heartbeatRequest) && !StringUtils.isEmpty(heartbeatResponse)) {
            pipeline.addLast(
                    new PingHandler(heartbeatRequest, heartbeatResponse),
                    new JsonClientHandler(initResData)
            );
            /*由下向上激活，但是接受消息时由上自下拦截*/
            /*pipeline.addLast(
                    new IdleStateHandler(0,5,0, TimeUnit.SECONDS),
                    new ClientIdleStateTrigger(heartbeatRequest),
                    new JsonClientHandler3(initResData),
                    new JsonClientHandler(initResData),
                    new JsonClientHandler2(initResData)
            );*/
        } else {
            // 短连接
            pipeline.addLast(new JsonSyncClientHandler());
        }
    }
}
