package com.kongbig.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lianggangda
 * @date 2020/3/12 10:12
 */
public interface RedisService {

    /**
     * 保存字符串数据
     *
     * @param key   键
     * @param value 值
     */
    void setString(String key, String value);

    /**
     * 获取字符串数据
     *
     * @param key 键
     * @return
     */
    String getString(String key);

    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 按键设置属性 和 属性值
     *
     * @param key     键
     * @param hashKey 属性
     * @param value   属性值
     */
    void put(String key, Object hashKey, Object value);

    /**
     * 按键设置map属性
     *
     * @param key 键
     * @param map map
     */
    void putAll(String key, Map<String, Object> map);

    /**
     * 按key和hashKeys删除
     *
     * @param key      键
     * @param hashKeys 属性（多个）
     * @return 成功个数
     */
    Long deleteHashKeys(Object key, Object... hashKeys);

    /**
     * 按键和属性获取属性值
     *
     * @param key     键
     * @param hashKey 属性
     * @return 属性值
     */
    Object get(String key, String hashKey);

    /**
     * 按键获取属性列表
     *
     * @param key 键
     * @return Set
     */
    Set hashKeys(String key);

    /**
     * 按键获取属性值列表
     *
     * @param key 键
     * @return List
     */
    List hashValues(String key);

}
