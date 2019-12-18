package com.kongbig.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBootApplication包含ComponentScan，默认扫描子包
 *
 * POST请求监控中心 http://127.0.0.1:8081/actuator/refresh 来刷新配置
 *
 * @author lianggangda
 * @date 2019/12/6 16:41
 */
@SpringBootApplication//申明这是一个Spring Boot项目
@EnableEurekaClient
@ComponentScan(basePackages = {"com.kongbig.cloud.controller", "com.kongbig.cloud.service", "com.kongbig.cloud.config"})//手动指定bean组件扫描范围
public class ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }

}
