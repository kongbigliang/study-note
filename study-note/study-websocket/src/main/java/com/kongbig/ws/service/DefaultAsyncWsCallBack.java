package com.kongbig.ws.service;

import com.kongbig.ws.netty.AsyncWsCallBack;
import com.kongbig.ws.netty.AsyncWsClient;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ws客户端操作的默认回调
 *
 * @author kongbig
 * @date 2019/11/9 22:25
 */
@Component
@Slf4j
public class DefaultAsyncWsCallBack implements AsyncWsCallBack {

    @Override
    public void doResponse(AsyncWsClient asyncWsClient, Object msg) {
        log.info("接收数据 ip:{}, port:{}, content:{}", asyncWsClient.getIp(), asyncWsClient.getPort(), msg);
    }

    @Override
    public void connected(AsyncWsClient asyncWsClient) {
        log.info(" ip:{}, port:{} 连接成功", asyncWsClient.getIp(), asyncWsClient.getPort());
    }

    @Override
    public void disConnected(AsyncWsClient asyncWsClient) {
        log.info("ip:{}, port:{} 断开连接", asyncWsClient.getIp(), asyncWsClient.getPort());
    }

    @Override
    public void exception(AsyncWsClient asyncWsClient, Throwable cause) {
        log.error("ip:{}, port:{} 连接异常", asyncWsClient.getIp(), asyncWsClient.getPort(), cause);
    }

    @Override
    public void heartBeat(AsyncWsClient asyncWsClient, IdleStateEvent event) {
        if (event.state().equals(IdleState.WRITER_IDLE)) {
            String jsonStr = "心跳json字符串";
            asyncWsClient.getChannel().write(new TextWebSocketFrame(jsonStr));
        }
    }

}
