package com.kongbig.ws.service;

import com.kongbig.ws.netty.AsyncWsClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ws通道service基类
 *
 * @author kongbig
 * @date 2019/11/10 9:18
 */
public abstract class BaseWsChannelService {

    /**
     * 客户端连接Map<wsUrl, client>
     */
    public ConcurrentHashMap<String, AsyncWsClient> asyncWsClientMap = new ConcurrentHashMap<>();

    /**
     * 获取webSocket字符串url
     *
     * @param ip   ws服务ip
     * @param port 端口
     * @return wsUrl
     */
    public String getWebSocketUrlStr(String ip, int port) {
        return String.format("ws://%s:%d", ip, port);
    }

    /**
     * 启动客户端时连上所有ws服务
     */
    public abstract void initWsConnection();

    /**
     * 移除ws服务
     *
     * @param ip   ws服务IP
     * @param port 端口
     */
    public abstract void removeWsConnection(String ip, int port);

    /**
     * 接入ws服务
     *
     * @param ip   ws服务IP
     * @param port 端口
     */
    public abstract void addWsConnection(String ip, int port);

}
