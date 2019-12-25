package com.kongbig.rabbitmq.routing;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * 路由模式的消费者1
 * 看作是前台系统
 *
 * @author lianggangda
 * @date 2019/12/25 15:02
 */
public class Receiver1 {

    private static final String QUEUE_NAME = "test_queue_direct_1";

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
        String routingKey1 = "update", routingKey2 = "delete";
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey1);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey2);

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动ack
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x1] Received '" + message + "'");
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
