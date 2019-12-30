package com.kongbig.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 生产者
 *
 * @author lianggangda
 * @date 2019/12/30 14:29
 */
public class SpringMain {

    public static void main(String[] args) throws Exception {
        String configLocation = "classpath:spring/rabbitmq-context.xml";
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        // RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
        // 发送消息
        template.convertAndSend("hello kongbig!");
        // 休眠1秒
        Thread.sleep(1000);
        // 容器销毁
        ctx.destroy();
    }

}
