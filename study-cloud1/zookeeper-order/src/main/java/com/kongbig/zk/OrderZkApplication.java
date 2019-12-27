package com.kongbig.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单服务
 *
 * @author lianggangda
 * @date 2019/12/27 10:35
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderZkApplication.class, args);
    }

}
