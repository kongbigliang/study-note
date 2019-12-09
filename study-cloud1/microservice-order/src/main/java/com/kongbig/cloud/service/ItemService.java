package com.kongbig.cloud.service;

import com.kongbig.cloud.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author kongbig
 * @date 2019/12/7 22:29
 */
@Service
public class ItemService {

    @Autowired
    private DiscoveryClient discoveryClient;

    // Spring框架对RESTful方式的http请求做了封装，来简化操作
    @Autowired
    private RestTemplate restTemplate;

    public Item queryItemById(Long id) {
        String serviceId = "app-item";
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            return null;
        }
        // 为了演示，在这里只获取第一个实例
        ServiceInstance serviceInstance = instances.get(0);
        String url = String.format("%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
        return this.restTemplate.getForObject(String.format("http://%s/item/%d", url, id), Item.class);
    }

}
