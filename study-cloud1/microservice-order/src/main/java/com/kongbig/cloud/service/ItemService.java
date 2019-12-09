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

    /*public Item queryItemById(Long id) {
        String serviceId = "app-item";
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            return null;
        }
        // 为了演示，在这里只获取第一个实例
        ServiceInstance serviceInstance = instances.get(0);
        String url = String.format("%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
        return this.restTemplate.getForObject(String.format("http://%s/item/%d", url, id), Item.class);
    }*/

    /**
     *
     * @param id
     * @return
     */
    public Item queryItemById(Long id) {
        // 该方法走eureka注册中心调用（这种方法必须先开启负载均衡@LoadBalanced）
        String itemUrl = "http://app-item/item/{id}";
        Item result = restTemplate.getForObject(itemUrl, Item.class, id);
        System.out.println("订单系统调用商品服务，result：" + result);
        return result;
    }

}
