package com.kongbig.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lianggangda
 * @date 2020/1/2 9:59
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("q_hello");
    }

}
