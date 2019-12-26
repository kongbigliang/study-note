package com.kongbig.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 开启配置服务
 *
 * 测试的访问地址为：
 * http://127.0.0.1:7788/microservice-test.properties
 *
 * 模拟git中的web钩子，通知ConfigServer更新消息：
 * http://127.0.0.1:7788/actuator/bus-refresh
 *
 * @author lianggangda
 * @date 2019/12/18 11:20
 */
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
