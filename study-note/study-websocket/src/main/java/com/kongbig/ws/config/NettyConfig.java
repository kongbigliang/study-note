package com.kongbig.ws.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * netty相关配置
 *
 * @author kongbig
 * @date 2019/11/10 9:10
 */
@Component
public class NettyConfig {

    /**
     * 初始化启动器
     *
     * @return
     */
    @Bean(name = "bootstrap")
    public Bootstrap bootstrap() {
        // 相当于线程池
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        return bootstrap;
    }

}
