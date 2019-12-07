package com.kongbig.ws.netty;

import com.kongbig.ws.service.DefaultAsyncWsCallBack;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * 用netty实现webSocket客户端
 *
 * @author kongbig
 * @date 2019/11/9 22:11
 */
@Setter
@Getter
@Slf4j
public class AsyncWsClient {

    /**
     * ws连接id
     */
    private String id;
    /**
     * ws服务ip
     */
    private String ip;
    /**
     * ws服务端口
     */
    private int port;
    /**
     * ws服务地址
     */
    private String wsUrl;
    /**
     * 读 心跳
     */
    private long readHeart = 60;
    /**
     * 写 心跳
     */
    private long writeHeart = 60;
    /**
     * 读写 心跳
     */
    private long bothHeart = 600;
    /**
     * 连接通道
     */
    private Channel channel;
    /**
     * 异步回调接口
     */
    private AsyncWsCallBack asyncWsCallBack = new DefaultAsyncWsCallBack();
    /**
     * 启动器
     */
    private Bootstrap bootstrap;

    /**
     * 构造器1
     *
     * @param ip
     * @param port
     * @param wsUrl
     * @param asyncWsCallBack
     * @param bootstrap
     */
    public AsyncWsClient(String ip, int port, String wsUrl, AsyncWsCallBack asyncWsCallBack, Bootstrap bootstrap) {
        this.ip = ip;
        this.port = port;
        this.wsUrl = wsUrl;
        this.asyncWsCallBack = asyncWsCallBack;
        this.bootstrap = bootstrap;
    }

    /**
     * 连接
     */
    public void connect() {
        this.channel = null;
        URI wsUri = null;
        try {
            wsUri = new URI(wsUrl);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        }

        // 初始化ws握手客户端
        WebSocketClientHandshaker webSocketClientHandshaker = WebSocketClientHandshakerFactory
                .newHandshaker(wsUri, WebSocketVersion.V13, null, true,
                        new DefaultHttpHeaders(), 100 * 1024 * 1024);
        // 初始化客户端请求处理器
        AsyncWsClientHandler handler = new AsyncWsClientHandler(webSocketClientHandshaker, this);
        this.bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("ping", new IdleStateHandler(readHeart, writeHeart, bothHeart, TimeUnit.SECONDS));
                pipeline.addLast(new HttpClientCodec());
                pipeline.addLast(new HttpObjectAggregator(65536));
                pipeline.addLast(handler);
            }
        });

        // 连接服务端
        Channel channel = bootstrap.connect(ip, port).addListener(new WsConnectionListener(bootstrap, this)).channel();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (null != this.channel && this.channel.isActive()) {
                this.channel.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
