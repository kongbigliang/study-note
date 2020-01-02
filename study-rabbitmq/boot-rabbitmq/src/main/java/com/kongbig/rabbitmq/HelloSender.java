package com.kongbig.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lianggangda
 * @date 2020/1/2 11:08
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        // 24小时制
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String context = "hello " + date;
        System.out.println("Sender: " + context);
        // 简单队列的情况下routingKey即为MQ名
        this.rabbitTemplate.convertAndSend("q_hello", context);
    }

}
