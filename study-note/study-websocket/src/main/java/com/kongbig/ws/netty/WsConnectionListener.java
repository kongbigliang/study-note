package com.kongbig.ws.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * ws客户端连接监听器（连不上时，定时触发）
 *
 * @author kongbig
 * @date 2019/11/10 9:03
 */
@Slf4j
public class WsConnectionListener implements ChannelFutureListener {

    private Bootstrap bootstrap;
    private AsyncWsClient asyncWsClient;

    /**
     * 初始化成员变量
     *
     * @param bootstrap
     * @param asyncWsClient
     */
    public WsConnectionListener(Bootstrap bootstrap, AsyncWsClient asyncWsClient) {
        this.bootstrap = bootstrap;
        this.asyncWsClient = asyncWsClient;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            // 自带定时器，尝试重连（1min）
            future.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    String ip = asyncWsClient.getIp();
                    int port = asyncWsClient.getPort();
                    String wsUrl = asyncWsClient.getWsUrl();
                    try {
                        log.warn("尝试重新连接ws服务 ip:{}, port:{}, wsUrl:{}", ip, port, wsUrl);
                        asyncWsClient.connect();
                    } catch (Exception e) {
                        log.warn("尝试重新连接ws服务 ip:{}, port:{}, wsUrl:{} 失败", ip, port, wsUrl, e);
                    }
                }
            }, 1L, TimeUnit.MINUTES);
        }
    }

}
