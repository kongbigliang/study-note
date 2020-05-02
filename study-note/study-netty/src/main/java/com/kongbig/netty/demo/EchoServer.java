package com.kongbig.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 构建服务器端，假设服务器接受客户端传来的信息，然后在控制台打印。
 *
 * @author lianggangda
 * @date 2020/1/13 11:28
 */
public class EchoServer {

    private final int port;

    /**
     * 在构造函数中传入需要监听的端口号。
     *
     * @param port
     */
    public EchoServer(int port) {
        this.port = port;
    }

    /**
     * 服务的启动方法：
     * 1.创建EventLoopGroup
     * 2.创建ServerBootstrap
     * 3.指定所使用的NIO传输Channel
     * 4.使用指定的端口设置套接字地址
     * 5.添加一个 ServerHandler 到 Channel 的 ChannelPipeline
     * 6.异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
     * 7.获取Channel的CloseFuture，并且阻塞当前线程直到它完成
     * 8.关闭EventLoopGroup，释放所有的资源
     *
     * @throws Exception
     */
    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 1.创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 3.指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 4.使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 5.添加一个 ServerHandler 到 Channel 的 ChannelPipeline。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 6.异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            ChannelFuture future = b.bind().sync();
            System.out.println(EchoServer.class.getName()
                    + " started and listening for connections on "
                    + future.channel().localAddress());
            // 7.获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } finally {
            // 8.关闭EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
