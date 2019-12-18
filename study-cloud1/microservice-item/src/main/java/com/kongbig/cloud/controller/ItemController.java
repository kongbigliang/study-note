package com.kongbig.cloud.controller;

import com.kongbig.cloud.config.JdbcConfigBean;
import com.kongbig.cloud.entity.Item;
import com.kongbig.cloud.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品controller
 * RestController = @Controller + @Response
 *
 * @author kongbig
 * @date 2019/12/7 14:52
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private JdbcConfigBean jdbcConfigBean;

    @Value("${spring.cloud.config.uri}")
    private String uri;

    /**
     * 对外提供接口服务，查询商品信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "item/{id}")
    public Item queryItemById(@PathVariable("id") Long id) {
        return this.itemService.queryItemById(id);
    }

    /**
     * 测试获取配置中心服务的配置
     *
     * @return
     */
    @GetMapping("testConfig")
    public String testConfig() {
        return this.jdbcConfigBean.toString();
    }

}
