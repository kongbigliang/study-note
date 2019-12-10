package com.kongbig.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lianggangda
 * @date 2019/12/10 10:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ItemServiceTest.class)
@Import(OrderApplication.class)
public class ItemServiceTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 测试ribbon做负载均衡
     * 按服务id选取服务实例，进行访问
     * 这里测试时，需要开启多个端口号不一样的item微服务
     */
    @Test
    public void test() {
        String serviceId = "app-item";
        for (int i = 0, len = 100; i < len; i++) {
            ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
            System.out.println("第" + (i + 1) + "次：" + serviceInstance.getHost() + ":" + serviceInstance.getPort());
        }
    }

}
