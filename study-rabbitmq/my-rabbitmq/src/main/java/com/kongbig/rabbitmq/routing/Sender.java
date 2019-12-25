package com.kongbig.rabbitmq.routing;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 路由模式的生产者
 *
 * @author lianggangda
 * @date 2019/12/25 14:56
 */
public class Sender {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException {
        // 获取连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机exchange
        String exchangeType = "direct";
        channel.exchangeDeclare(EXCHANGE_NAME, exchangeType);

        // 消息内容
        String message1 = "删除商品", message2 = "新增商品";
        String routingKey1 = "delete", routingKey2 = "insert";
        channel.basicPublish(EXCHANGE_NAME, routingKey1, null, message1.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey2, null, message2.getBytes());
        System.out.println(" [x] Sent '" + message1 + "'");
        System.out.println(" [x] Sent '" + message2 + "'");

        channel.close();
        connection.close();

    }

}
