package com.kongbig.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lianggangda
 * @date 2020/1/2 14:44
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kongbig"})
public class RabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplication.class, args);
    }

}
