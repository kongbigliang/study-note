package com.kongbig.cloud.fallback;

import com.kongbig.cloud.entity.Item;
import com.kongbig.cloud.feign.ItemFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 此类中的方法专门用于服务降级，该类一般要实现调用远程服务的接口
 *
 * @author lianggangda
 * @date 2019/12/10 17:31
 */
@Component
public class ItemServiceFallBack implements ItemFeignClient {

    /**
     * 服务降级的方法要和原方法一致(名称、参数列表)
     *
     * @param id
     * @return
     */
    @Override
    public Item queryItemById(Long id) {
        return new Item(null, "服务降级方法queryItemById", null, "服务降级方法queryItemById", null);
    }

    @Override
    public List<Item> queryItemList(String title, Long price) {
        return null;
    }

    @Override
    public List<Item> queryItemList(Map<String, Object> params) {
        return null;
    }

}
