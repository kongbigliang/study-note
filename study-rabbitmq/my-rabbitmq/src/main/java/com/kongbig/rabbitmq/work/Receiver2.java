package com.kongbig.rabbitmq.work;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Work模式的消费者2
 *
 * @author lianggangda
 * @date 2019/12/24 17:41
 */
public class Receiver2 {

    private static final String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 同一服务器只会发送一条消息给消费者
        // channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，false表示手动返回完成状态，true表示自动（autoAck）
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠1秒
            Thread.sleep(1000);
            // 返回确认状态，注释掉表示使用自动确认模式（autoAck）
            // channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
