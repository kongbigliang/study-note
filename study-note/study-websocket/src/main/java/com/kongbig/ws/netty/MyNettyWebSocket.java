package com.kongbig.ws.netty;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/6/1 16:33
 */
@ServerEndpoint(path = "/ws/{userId}")
@Slf4j
public class MyNettyWebSocket {

    public static Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String userId, @PathVariable Map pathMap) {
        session.setSubprotocols("stomp");
        if (!"ok".equals(req)) {
            System.out.println("Authentication failed!");
            session.close();
        }
    }

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String userId, @PathVariable Map pathMap) {
        log.info("onOpen, id为[{}]的用户建立ws连接...", userId);
        USER_SESSION_MAP.put(userId, session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String userId = null;
        for (Map.Entry<String, Session> entry : USER_SESSION_MAP.entrySet()) {
            if (session == entry.getValue()) {
                userId = entry.getKey();
            }
        }
        log.info("onClose, id为[{}]的用户关闭ws连接...", userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        String userId = null;
        for (Map.Entry<String, Session> entry : USER_SESSION_MAP.entrySet()) {
            if (session == entry.getValue()) {
                userId = entry.getKey();
            }
        }
        log.info("onMessage, 收到用户id为[{}]发送的消息: [{}]", userId, message);
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}

