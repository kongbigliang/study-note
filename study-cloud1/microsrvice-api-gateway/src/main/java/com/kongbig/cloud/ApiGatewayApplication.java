package com.kongbig.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 访问：http://127.0.0.1:8087/item-service/item/2
 * item-service路由到商品服务：127.0.0.1:8081
 *
 * @author lianggangda
 * @date 2019/12/11 13:50
 */
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
