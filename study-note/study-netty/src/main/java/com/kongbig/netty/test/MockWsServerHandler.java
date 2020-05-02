package com.kongbig.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.http.HttpStatus;

import javax.xml.soap.Text;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 处理器
 *
 * @author kongbig
 * @date 2019/11/9 9:46
 */
public class MockWsServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            // 传统的HTTP接入
            handleHttpMessage(ctx, msg);
        } else if (msg instanceof WebSocketFrame) {
            // webSocket接入
            handleSebSocketMessage(ctx, msg);
        }
    }

    /**
     * 处理webSocket中的http信息
     *
     * @param ctx 上下文
     * @param msg 消息
     */
    private void handleHttpMessage(ChannelHandlerContext ctx, Object msg) {
        // 传统http接入
        FullHttpRequest request = (FullHttpRequest) msg;

        // 如果http解码失败，返回http异常
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
    }

    /**
     * http返回
     *
     * @param ctx
     * @param request
     * @param response
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        // 返回应答给客户端
        if (response.status().code() != HttpStatus.OK.value()) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            buf.release();
            HttpUtil.setContentLength(response, response.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture future = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) || response.status().code() != HttpStatus.OK.value()) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 处理webSocket中的webSocket消息
     *
     * @param ctx 上下文
     * @param msg 消息
     */
    private void handleSebSocketMessage(ChannelHandlerContext ctx, Object msg) {
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            System.out.println("ws服务器接收到来自客户端的文本信息为" + textFrame.content());
            // 返回消息给客户端
            ctx.writeAndFlush(new TextWebSocketFrame("我是服务器；我是服务器；我是服务器；"));
        } else {
            // 其它WebSocketFrame继续判断
            System.out.println("其它WebSocketFrame继续判断");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("服务器：channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("服务器：channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        System.out.println("服务器：exceptionCaught");
        throwable.printStackTrace();
    }

}
