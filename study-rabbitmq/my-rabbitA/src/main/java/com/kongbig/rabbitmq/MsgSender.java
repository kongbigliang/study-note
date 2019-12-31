package com.kongbig.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息生产者
 * 修改xml配置文件，向不同的交换机发送东西
 *
 * @author lianggangda
 * @date 2019/12/31 9:32
 */
public class MsgSender {

    /**
     * 在A系统中发送消息到交换机
     * <p>
     * 队列和交换机的绑定关系实现：
     * 1、在配置文件中将队列和交换机完成绑定
     * 2、可以在管理界面中完成绑定
     *  a)绑定关系如果发生变化，需要修改配置文件，并且服务需要重启
     *  b)管理更加灵活
     *  c)更容易对绑定关系的权限管理，流程管理
     * <p>
     * 本例选择第2种方式
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String configLocation = "classpath:spring/rabbitmq-context.xml";
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        // RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 发送消息
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("type", "1");
        msg.put("date", date);
        template.convertAndSend("type.2", JSON.toJSONString(msg));
        // 休眠1秒
        Thread.sleep(1000);
        // 容器销毁
        ctx.destroy();
    }

}
