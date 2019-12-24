package com.kongbig.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author lianggangda
 * @date 2019/12/24 17:09
 */
public class ConnectionUtil {

    private static final String HOST = "192.168.25.213";
    private static final int PORT = 5672;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";
    private static final String VIRTUAL_HOST = "testhost";

    public static Connection getConnection() throws IOException {
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost(HOST);
        // 端口
        factory.setPort(PORT);
        // 设置账号信息：用户名、密码、VirtualHost
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        // 通过工厂获取连接
        return factory.newConnection();
    }

}
