package com.pf.plat.exchange.socket;

import com.google.common.collect.Lists;
import com.pf.enums.DataFormatsEnum;
import com.pf.enums.SysStatusCode;
import com.pf.exception.Asserts;
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
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @ClassName : SocketClient
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/10/16-9:24
 */
public class SocketClient implements ExchangeClient {

    /*连接类型*/
    public static final String LONG_CONN = "1";
    public static final String SHORT_CONN = "2";

    //必要参数
    private final String host;
    private final Integer port;
    private DataFormatsEnum requestDataFormatsEnum;
    private DataFormatsEnum responseDataFormatsEnum;
    //可选参数
    private Boolean ssl;
    private String connType;
    private List<String> extendNo; // 业务识别号数组
    private String matchingRule; // 匹配规则
    private RetryPolicy retryPolicy; // 重连策略
    private String charsets;
    private String heartbeatRequest;
    private String heartbeatResponse;
    private String initResData;

    EventLoopGroup group = null;
    Bootstrap bootstrap = null;
    Channel channel = null;

    private SocketClient(Builder builder) throws Exception{
        this.host = builder.host;
        this.port = builder.port;
        this.requestDataFormatsEnum = builder.requestDataFormatsEnum != null ? builder.requestDataFormatsEnum: DataFormatsEnum.JSON;
        this.responseDataFormatsEnum = builder.responseDataFormatsEnum != null ? builder.responseDataFormatsEnum: DataFormatsEnum.JSON;
        this.ssl = builder.ssl != null ? builder.ssl: false;
        this.connType = builder.connType != null ? builder.connType: LONG_CONN;
        this.extendNo = builder.extendNo != null ? builder.extendNo: Lists.newArrayList();
        this.matchingRule = StringUtils.isNotBlank(builder.matchingRule)? builder.matchingRule: null;
        this.retryPolicy = builder.retryPolicy != null ? builder.retryPolicy: new ExponentialBackOffRetry(3000, Integer.MAX_VALUE, 60 * 1000);
        this.charsets = StringUtils.isNotBlank(builder.charsets)? builder.charsets: "UTF-8";
        this.heartbeatRequest = builder.heartbeatRequest;
        this.heartbeatResponse = builder.heartbeatResponse;
        this.initResData = builder.initResData;
        this.init();
        this.connect();
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public static class Builder {
        //必要参数
        private final String host;
        private final Integer port;
        //可选参数
        private Boolean ssl;
        private DataFormatsEnum requestDataFormatsEnum;
        private DataFormatsEnum responseDataFormatsEnum;
        private String connType;
        private List<String> extendNo; // 扩展识别号数组
        private String matchingRule; // 匹配规则
        private RetryPolicy retryPolicy; // 重连策略
        private String charsets;
        private String heartbeatRequest;
        private String heartbeatResponse;
        private String initResData;

        public Builder(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public Builder ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public Builder requestDataFormatsEnum(DataFormatsEnum requestDataFormatsEnum) {
            this.requestDataFormatsEnum = requestDataFormatsEnum;
            return this;
        }

        public Builder responseDataFormatsEnum(DataFormatsEnum responseDataFormatsEnum) {
            this.responseDataFormatsEnum = responseDataFormatsEnum;
            return this;
        }

        public Builder connType(String connType) {
            this.connType = connType;
            return this;
        }

        public Builder retryPolicy(RetryPolicy retryPolicy) {
            this.retryPolicy = retryPolicy;
            return this;
        }

        public Builder extendNo(List<String> extendNo) {
            this.extendNo = extendNo;
            return this;
        }

        public Builder charsets(String charsets) {
            this.charsets = charsets;
            return this;
        }

        public Builder matchingRule(String matchingRule) {
            this.matchingRule = matchingRule;
            return this;
        }

        public Builder heartbeatRequest(String heartbeatRequest) {
            this.heartbeatRequest = heartbeatRequest;
            return this;
        }

        public Builder heartbeatResponse(String heartbeatResponse) {
            this.heartbeatResponse = heartbeatResponse;
            return this;
        }

        public Builder initResData(String initResData) {
            this.initResData = initResData;
            return this;
        }

        public SocketClient build() throws Exception{
            return new SocketClient(this);
        }
    }

    private void init() throws Exception {
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
                        .extendNo(extendNo)
                        .initResData(initResData)
                        .matchingRule(matchingRule)
                        .build();
                this.bootstrap.handler(jsonClientInitializer);
            }
        } else {
            Asserts.fail(SysStatusCode.PLAT_CONN_TYPE_EXCEPTION);
        }
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
                    if(channelFuture.isSuccess()) {
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
