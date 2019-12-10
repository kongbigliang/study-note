package com.kongbig.cloud.service;

import com.kongbig.cloud.entity.Item;
import com.kongbig.cloud.feign.ItemFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ItemService {

    @Autowired
    private DiscoveryClient discoveryClient;

    // Spring框架对RESTful方式的http请求做了封装，来简化操作
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 底层生成代理类
     */
    @Autowired
    private ItemFeignClient itemFeignClient;

    /**
     * 服务发现中获取服务实例
     *
     * @param id
     * @return
     */
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

    /**
     * 负载均衡（用实例名）
     *
     * @param id
     * @return
     */
    public Item queryItemById2(Long id) {
        // 该方法走eureka注册中心调用（这种方法必须先开启负载均衡@LoadBalanced）
        String itemUrl = "http://app-item/item/{id}";
        Item result = restTemplate.getForObject(itemUrl, Item.class, id);
        System.out.println("订单系统调用商品服务，result：" + result);
        return result;
    }

    /**
     * hystrix容错处理。
     *
     * 改造成使用feign请求商品服务
     *
     * 实际开发中fallback 方法不会直接写在接口方法所在类里，那样太杂乱，再改造为回调。
     *
     * @param id
     * @return
     */
    // @HystrixCommand(fallbackMethod = "queryItemByIdFallBackMethod")
    public Item queryItemById3(Long id) {
        String itemUrl = "http://app-item/item/{id}";
        // Item result = restTemplate.getForObject(itemUrl, Item.class, id);
        Item result = itemFeignClient.queryItemById(id);
        System.out.println("====HystrixCommand queryItemById-线程池名称：" + Thread.currentThread().getName()
                + "订单系统调用商品服务，result：" + result);
        return result;
    }

    /**
     * 请求失败执行的方法
     * fallBackMethod的方法参数个数要和原方法一致
     *
     * @param id
     * @return
     */
    public Item queryItemByIdFallBackMethod(Long id) {
        log.error("queryItemByIdFallBackMethod");
        return new Item(id, "查询商品信息出错", null, null, null);
    }

}
