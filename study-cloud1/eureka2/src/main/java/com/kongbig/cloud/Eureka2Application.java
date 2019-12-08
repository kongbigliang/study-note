package com.kongbig.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 * EnableEurekaServer：申明这是一个Eureka服务
 *
 * 以通过停止其中一个Eureka server服务进行测试，结果会发现集群是高可用。
 * 即：启动2个Eureka服务端(注册中心)，1个商品服务（item），1个订单服务（order）。
 * 停掉其中一个Eureka服务端后，访问另外一个Eureka服务端页面可以看到item服务立马切换到此注册中心，
 * 订单服务照样可以访问商品服务，浏览器输入http://localhost:8082/order/201810300001还能获取到商品数据（因为商品服务同时注册到了2个注册中心）。
 *
 * @author lianggangda
 * @date 2019/12/6 15:14
 */
@SpringBootApplication
@EnableEurekaServer
// 把认证关闭，客户端才能连接服务端
// @EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class Eureka2Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka2Application.class, args);
    }

}
