package com.kongbig.cloud.controller;

import com.kongbig.cloud.entity.Order;
import com.kongbig.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kongbig
 * @date 2019/12/7 22:32
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "order/{orderId}")
    public Order queryOrderById(@PathVariable("orderId") String orderId) {
        return this.orderService.queryOrderById(orderId);
    }

    /**
     * 测试发现，加了@HystrixCommand注解的方法和普通方法不是共用的线程池，是隔离的。
     *
     * 把商品服务关闭进行测试，代码将执行@HystrixCommand的容错方法
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "order2/{orderId}")
    public Order queryOrderById2(@PathVariable("orderId") String orderId) {
        return this.orderService.queryOrderByIdx(orderId);
    }

}
