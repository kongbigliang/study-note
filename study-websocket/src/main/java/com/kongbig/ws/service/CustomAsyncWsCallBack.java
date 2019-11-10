package com.kongbig.ws.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kongbig.ws.netty.AsyncWsCallBack;
import com.kongbig.ws.netty.AsyncWsClient;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义回调
 * 此类应该写在业务系统当中，业务系统将本module作为依赖导进来
 *
 * @author kongbig
 * @date 2019/11/10 10:01
 */
@Component
@Slf4j
public class CustomAsyncWsCallBack implements AsyncWsCallBack {

    @Override
    public void doResponse(AsyncWsClient asyncWsClient, Object msg) {
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            log.info("接收数据 ip:{}, port:{}, content:{}", asyncWsClient.getIp(), asyncWsClient.getPort(), textFrame.text());
        } else {
            log.info("接收数据 ip:{}, port:{}, content:{}", asyncWsClient.getIp(), asyncWsClient.getPort(), msg);
        }
    }

    @Override
    public void connected(AsyncWsClient asyncWsClient) {
        log.info("ip:{}, port:{} 连接成功", asyncWsClient.getIp(), asyncWsClient.getPort());
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
            // TODO 根据业务系统定义的协议而定
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", "keepLive");
            jsonObject.put("type", "request");
            asyncWsClient.getChannel().write(new TextWebSocketFrame(JSON.toJSONString(jsonObject)));
        }
    }

}
