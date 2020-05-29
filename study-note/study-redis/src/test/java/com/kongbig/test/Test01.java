package com.kongbig.test;

import redis.clients.jedis.Jedis;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/5/29 11:46
 */
public class Test01 {

    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.234.33", 56379);
        jedis.auth("Mon56BuEcXzZ");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("runoobkey"));

    }

}
