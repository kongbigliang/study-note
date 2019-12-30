package com.kongbig.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author lianggangda
 * @date 2019/12/30 10:58
 */
@RestController
@Api("SwaggerDemo控制器")
public class SwaggerController {

    /**
     * Swagger演示
     *
     * @param msg
     * @return
     */
    @GetMapping("/swaggerIndex")
    @ApiOperation("Swagger演示")
    public String swaggerIndex(String msg) {
        return "This is swaggerIndex!" + msg;
    }

    /**
     * 获取商品详情
     *
     * @param itemName
     * @return
     */
    @PostMapping("/getItemInfo")
    @ApiOperation("获取商品详情")
    @ApiImplicitParam(name = "itemName", value = "商品名称", required = true, dataType = "String")
    public String getItemInfo(String itemName) {
        return "商品名：" + itemName + " 商品价格：" + new BigDecimal(Math.random() * 100)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
