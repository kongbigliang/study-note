package com.kongbig.ws.netty;

import io.netty.handler.timeout.IdleStateEvent;

/**
 * ws客户端操作的回调接口
 *
 * @author kongbig
 * @date 2019/11/9 22:18
 */
public interface AsyncWsCallBack {

    /**
     * 请求回调
     *
     * @param asyncWsClient ws客户端
     * @param msg           消息
     */
    public void doResponse(AsyncWsClient asyncWsClient, Object msg);

    /**
     * 连接成功
     *
     * @param asyncWsClient ws客户端
     */
    public void connected(AsyncWsClient asyncWsClient);

    /**
     * 连接断开
     *
     * @param asyncWsClient ws客户端
     */
    public void disConnected(AsyncWsClient asyncWsClient);

    /**
     * 连接异常
     * @param asyncWsClient ws客户端
     * @param cause         异常
     */
    public void exception(AsyncWsClient asyncWsClient, Throwable cause);

    /**
     * 心跳检测
     *
     * @param asyncWsClient ws客户端
     * @param event         状态事件
     */
    public void heartBeat(AsyncWsClient asyncWsClient, IdleStateEvent event);

}
