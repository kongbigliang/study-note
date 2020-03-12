package com.kongbig.redis.controller;

import com.kongbig.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author lianggangda
 * @date 2020/3/12 10:59
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/put")
    public void put() {
        String token = "token";
        redisService.put(token, "username", "kongbig");
        redisService.put(token, "age", 25);

        System.out.println(redisService.get(token, "username"));
        System.out.println("\n输出全部hashKey:");
        Set set = redisService.hashKeys(token);
        set.forEach(System.out::println);

        System.out.println("\n输出全部hashValue:");
        List list = redisService.hashValues(token);
        list.forEach(System.out::println);

        System.out.println("\n删除age字段:");
        Long age = redisService.delete(token, "age");
        System.out.println(1 == age ? true : false);
    }

}
