package com.kongbig.cloud.feign;

import com.kongbig.cloud.entity.Item;
import com.kongbig.cloud.fallback.ItemServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 申明这是一个Feign客户端，并且指明服务id
 * <p>
 * 实际开发中ItemFeignClient一般直接继承(extends)服务提供方的接口
 * 以避免代码重复（例如Item工程会以jar包的形式提供ItemService接口）
 *
 * @author lianggangda
 * @date 2019/12/10 16:31
 */
@FeignClient(value = "app-item", fallback = ItemServiceFallBack.class)
public interface ItemFeignClient {

    /**
     * 这里定义了类似于SpringMVC用法的方法，就可以进行restful方式的调用。
     *
     * @param id
     * @return
     */
    @GetMapping("/item/{id}")
    Item queryItemById(@PathVariable("id") Long id);

    /**
     * 多参数查找
     *
     * @param title
     * @param price
     * @return
     */
    @GetMapping("/item")
    List<Item> queryItemList(@RequestParam("title") String title, @RequestParam("price") Long price);

    /**
     * 多参数查找
     *
     * @param params
     * @return
     */
    @GetMapping("/item")
    List<Item> queryItemList(@RequestParam Map<String, Object> params);

}
