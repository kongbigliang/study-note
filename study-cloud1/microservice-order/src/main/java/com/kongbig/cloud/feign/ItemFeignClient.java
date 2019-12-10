package com.kongbig.cloud.feign;

import com.kongbig.cloud.entity.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 申明这是一个Feign客户端，并且指明服务id
 *
 * @author lianggangda
 * @date 2019/12/10 16:31
 */
@FeignClient(value = "app-item")
public interface ItemFeignClient {

    /**
     * 这里定义了类似于SpringMVC用法的方法，就可以进行restful方式的调用。
     * @param id
     * @return
     */
    @GetMapping("/item/{id}")
    Item queryItemById(@PathVariable("id") Long id);

}
