package com.kongbig.cloud.service;

import com.kongbig.cloud.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author kongbig
 * @date 2019/12/7 22:29
 */
@Service
public class ItemService {

    // Spring框架对RESTful方式的http请求做了封装，来简化操作
    @Autowired
    private RestTemplate restTemplate;

    public Item queryItemById(Long id) {
        return this.restTemplate.getForObject("http://127.0.0.1:8081/item/" + id, Item.class);
    }

}
