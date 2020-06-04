package com.kongbig.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author lianggangda
 * @date 2020/1/13 14:27
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 客户端启动程序的顺序：
     * 1.创建Bootstrap
     * 2.指定EventLoopGroup以处理客户端事件；需要适用于NIO的实现
     * 3.定义Channel的传输模式为NIO（Non-BlockingInputOutput）
     * 4.设置服务器的InetSocketAddress
     * 5.在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
     * 6.连接到远程节点，阻塞等待直到连接完成
     * 7.阻塞，直到Channel关闭
     * 8.关闭线程池并且释放所有的资源
     *
     * @throws Exception
     */
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 1.创建Bootstrap
            Bootstrap b = new Bootstrap();
            // 2.指定EventLoopGroup以处理客户端事件；需要适用于NIO的实现
            b.group(group)
                    // 3.定义Channel的传输模式为NIO（Non-BlockingInputOutput）
                    .channel(NioSocketChannel.class)
                    // 4.设置服务器的InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 5.在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 6.连接到远程节点，阻塞等待直到连接完成
            ChannelFuture future = b.connect().sync();
            // 7.阻塞，直到Channel关闭
            future.channel().closeFuture().sync();
        } finally {
            // 8.关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
