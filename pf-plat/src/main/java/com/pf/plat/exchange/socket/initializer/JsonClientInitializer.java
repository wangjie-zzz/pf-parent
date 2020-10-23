package com.pf.plat.exchange.socket.initializer;

import com.pf.plat.exchange.socket.handler.JsonClientHandler;
import com.pf.plat.exchange.socket.handler.JsonSyncClientHandler;
import com.pf.plat.exchange.socket.handler.PingHandler;
import com.pf.plat.exchange.socket.handler.ReconnectHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.List;

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

    private final List<String> extendNo;

    private final String matchingRule;

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
        if(!StringUtils.isEmpty(heartbeatRequest) && !StringUtils.isEmpty(heartbeatResponse)
            && !CollectionUtils.isEmpty(extendNo)) {
            pipeline.addLast(
                    new PingHandler(heartbeatRequest, heartbeatResponse),
                    new JsonClientHandler(extendNo, matchingRule, initResData)
            );
        } else {
            // 短连接
            pipeline.addLast(new JsonSyncClientHandler());
        }
    }
}
