package com.kongbig.cloud.controller;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author lianggangda
 * @date 2019/12/30 11:18
 */
@RestController
@Api("订单服务接口")
@EnableSwagger2Doc
public class OrderApiController {

    @PostMapping("/putOrderInfo")
    @ApiOperation("提交订单")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "String")
    public String putOrderInfo(String orderNo) {
        return "订单：" + orderNo + " 已提交，商品总价：" + new BigDecimal(Math.random() * 1000)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @GetMapping("/getOrderInfo")
    @ApiOperation("获取订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "String")
    public String getOrderInfo(String orderNo) {
        return "订单：" + orderNo + " 商品总价格：" + new BigDecimal(Math.random() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
