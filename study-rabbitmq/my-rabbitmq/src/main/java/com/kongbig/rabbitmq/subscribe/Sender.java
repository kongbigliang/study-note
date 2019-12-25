package com.kongbig.rabbitmq.subscribe;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 订阅模式的消息生产者
 * （看作是后台系统）
 * 向交换机中发送消息
 *
 * 注意：消息发送到没有队列绑定的交换机时，消息将丢失，因为交换机没有存储能力，消息只能存在于队列中
 *
 * 测试结果：
 * 同一个消息被多个消费者获取。一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费到消息。
 *
 * @author lianggangda
 * @date 2019/12/25 11:19
 */
public class Sender {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException {
        // 获取连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        String type = "fanout";
        channel.exchangeDeclare(EXCHANGE_NAME, type);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

}
