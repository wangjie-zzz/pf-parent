package com.pf.plat.exchange.socket;

import com.google.common.collect.Lists;
import com.pf.enums.DataFormatsEnum;
import com.pf.enums.SysStatusCode;
import com.pf.util.Asserts;
import com.pf.plat.exchange.ExchangeClient;
import com.pf.plat.exchange.socket.config.ExponentialBackOffRetry;
import com.pf.plat.exchange.socket.config.RetryPolicy;
import com.pf.plat.exchange.socket.handler.JsonSyncClientHandler;
import com.pf.plat.exchange.socket.handler.ReconnectHandler;
import com.pf.plat.exchange.socket.initializer.JsonClientInitializer;
import com.pf.plat.model.domain.RequestTempFile;
import com.pf.plat.model.domain.ResponseTempFile;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Builder;
import java.nio.charset.Charset;

/**
 * @ClassName : SocketClient
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-9:24
 */
@Builder
public class SocketClient implements ExchangeClient {

    /*连接类型*/
    public static final String LONG_CONN = "1";
    public static final String SHORT_CONN = "2";

    //必要参数
    private final String host = "127.0.0.1";
    private final Integer port = 8077;
    private DataFormatsEnum requestDataFormatsEnum;
    private DataFormatsEnum responseDataFormatsEnum;
    //可选参数
    private Boolean ssl;
    private String connType;
    private RetryPolicy retryPolicy; // 重连策略
    private String charsets;
    private String heartbeatRequest;
    private String heartbeatResponse;
    private String initResData;

    EventLoopGroup group = null;
    Bootstrap bootstrap = null;
    Channel channel = null;

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public void init() throws Exception {
        final SslContext sslContext;
        if (ssl) {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslContext = null;
        }
        // Configure the client.
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false);
        if(SHORT_CONN.equals(this.connType)) {
            if(DataFormatsEnum.JSON == this.requestDataFormatsEnum && DataFormatsEnum.JSON == this.responseDataFormatsEnum) {
                JsonClientInitializer jsonClientInitializer = JsonClientInitializer.builder()
                        .reconnectHandler(new ReconnectHandler(this))
                        .sslContext(sslContext)
                        .charset(Charset.forName(charsets))
                        .build();
                this.bootstrap.handler(jsonClientInitializer);
            }
        } else if (LONG_CONN.equals(this.connType)) {
            if(DataFormatsEnum.JSON == this.requestDataFormatsEnum && DataFormatsEnum.JSON == this.responseDataFormatsEnum) {
                JsonClientInitializer jsonClientInitializer = JsonClientInitializer.builder()
                        .reconnectHandler(new ReconnectHandler(this))
                        .sslContext(sslContext)
                        .charset(Charset.forName(charsets))
                        .heartbeatRequest(heartbeatRequest)
                        .heartbeatResponse(heartbeatResponse)
                        .initResData(initResData)
                        .build();
                this.bootstrap.handler(jsonClientInitializer);
            }
        } else {
            Asserts.fail(SysStatusCode.PLAT_CONN_TYPE_EXCEPTION);
        }
        this.connect();
    }
    public void connect () {
        if(SHORT_CONN.equals(this.connType)) {
            try {
                this.channel = this.bootstrap.connect(host, port).sync().channel();
                this.channel.closeFuture().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        group.shutdownGracefully();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                Asserts.fail(SysStatusCode.PLAT_SOCKET_CLIENT_INIT_ERROR);
            }
        } else if (LONG_CONN.equals(this.connType)) {
            ChannelFuture future = this.bootstrap.connect(host, port);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()) {
                        channelFuture.channel().pipeline().fireChannelInactive();
                    }
                }
            });
            this.channel = future.channel();
        } else {
            Asserts.fail(SysStatusCode.PLAT_CONN_TYPE_EXCEPTION);
        }

    }
    @Override
    public String sendData(String data) throws Exception {
        String response = null;
        this.channel.writeAndFlush(data);
        if(SHORT_CONN.equals(this.connType)) {
            if(DataFormatsEnum.JSON == this.requestDataFormatsEnum && DataFormatsEnum.JSON == this.responseDataFormatsEnum) {
                JsonSyncClientHandler jsonSyncClientHandler = (JsonSyncClientHandler) this.channel.pipeline().last();
                return jsonSyncClientHandler.getResponse();
            }
        }
        return null;
    }

    @Override
    public ResponseTempFile download(String data) throws Exception {
        return null;
    }

    @Override
    public String upload(String data, RequestTempFile requestTempFile) throws Exception {
        return null;
    }
}
