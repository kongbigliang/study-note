package com.kongbig.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 商品服务
 *
 * @author lianggangda
 * @date 2019/12/27 9:43
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ItemZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemZkApplication.class, args);
    }

}
