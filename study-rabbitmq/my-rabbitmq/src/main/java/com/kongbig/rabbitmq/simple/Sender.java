package com.kongbig.rabbitmq.simple;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 简单队列的生产者
 *
 * @author lianggangda
 * @date 2019/12/24 17:08
 */
public class Sender {

    private static final String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建mq通道
        Channel channel = connection.createChannel();
        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        // 关闭通道和连接
        channel.close();
        connection.close();
    }

}
