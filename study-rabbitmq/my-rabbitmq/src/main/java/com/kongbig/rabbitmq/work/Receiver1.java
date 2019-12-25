package com.kongbig.rabbitmq.work;

import com.kongbig.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Work模式的消费者1
 * <p>
 * 轮询分发：默认情况下，RabbitMQ将逐个发送消息到在序列中的下一个消费者
 * (而不考虑每个任务的时长等等，且是提前一次性分配，并非一个一个分配)。
 * 平均每个消费者获得相同数量的消息。这种方式分发消息机制称为Round-Robin（轮询）。
 * <p>
 * 公平分发：使用公平分发，必须关闭自动应答，改为手动应答。
 *
 * @author lianggangda
 * @date 2019/12/24 17:41
 */
public class Receiver1 {

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
            System.out.println(" [y] Received '" + message + "'");
            // 休眠
            Thread.sleep(10);
            // 返回确认状态，注释掉表示使用自动确认模式（autoAck）
            // channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

}
