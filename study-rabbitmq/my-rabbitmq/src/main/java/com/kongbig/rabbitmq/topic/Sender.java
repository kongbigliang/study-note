package com.kongbig.rabbitmq.topic;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 主题模式（通配符模式）的生产者
 *
 * @author lianggangda
 * @date 2019/12/25 15:58
 */
public class Sender {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException {
        // 获取连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机exchange
        String type = "topic";
        channel.exchangeDeclare(EXCHANGE_NAME, type);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "routingKey.1", null, message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "haha.1", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
