package com.kongbig.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * netty实现webSocket服务端demo
 *
 * @author kongbig
 * @date 2019/11/9 9:30
 */
public class MockWsServer {

    private int port;

    public MockWsServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(boosGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                // Http消息编码解码
                pipeline.addLast("http-codec", new HttpServerCodec());
                // Http消息组装
                pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                // WebSocket通信支持
                pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                pipeline.addLast(new MockWsServerHandler());
            }
        });

        // 监听端口
        ChannelFuture future = serverBootstrap.bind(port);
        future.awaitUninterruptibly();
        // 堵塞线程，保持长连接
        future.channel().closeFuture().sync();
    }

}
