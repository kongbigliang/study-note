package com.kongbig.ws.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * webSocket客户端的请求处理器
 *
 * @author kongbig
 * @date 2019/11/9 22:55
 */
@ChannelHandler.Sharable
@Slf4j
public class AsyncWsClientHandler extends SimpleChannelInboundHandler<Object> {

    private final WebSocketClientHandshaker handShaker;
    private ChannelPromise handShakeFuture;

    private AsyncWsClient asyncWsClient;

    /**
     * 构造器（初始化handShaker）
     *
     * @param handShaker
     */
    public AsyncWsClientHandler(WebSocketClientHandshaker handShaker, AsyncWsClient asyncWsClient) {
        this.handShaker = handShaker;
        this.asyncWsClient = asyncWsClient;
    }

    public ChannelFuture handShakeFuture() {
        return handShakeFuture;
    }

    /**
     * Handler本身被添加到ChannelPipeline时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handShakeFuture = ctx.newPromise();
    }

    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive: 客户端连接建立");
        Channel ch = ctx.channel();
        // 在通道连接成功后发送握手连接
        handShaker.handshake(ch);
        asyncWsClient.setChannel(ch);
        super.channelActive(ctx);
    }

    /**
     * Handler本身被从ChannelPipeline中删除时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        // 这里是第一次使用http连接成功的时候
        if (!handShaker.isHandshakeComplete()) {
            handShaker.finishHandshake(channel, (FullHttpResponse) msg);
            log.info("WebSocket Client connected: ws客户端连接成功");
            handShakeFuture.setSuccess();
            return;
        }

        // 这里是第一次使用HTTP连接失败的时候
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        // 处理消息
        asyncWsClient.getAsyncWsCallBack().doResponse(asyncWsClient, msg);
    }

    /**
     * 异常发生
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        asyncWsClient.getAsyncWsCallBack().exception(asyncWsClient, cause);
    }

    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接中断1秒重连");
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                log.warn("尝试重新连接ws服务 ip:{}, port:{}, wsUrl:{}", asyncWsClient.getIp(), asyncWsClient.getPort(), asyncWsClient.getWsUrl());
                asyncWsClient.connect();
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    /**
     * 通道 读取 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 定时任务
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            asyncWsClient.getAsyncWsCallBack().heartBeat(asyncWsClient, event);
        }
    }

}
