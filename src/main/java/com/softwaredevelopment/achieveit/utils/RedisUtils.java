package com.softwaredevelopment.achieveit.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * set Object
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, JsonUtils.toJSONString(value));
    }

    /**
     * get String
     */
    public String getValue(String key) {
        return getValue(key, String.class);
    }

    /**
     * get Object
     */
    public  <T> T getValue(String key, Class<T> clazz) {
        return JsonUtils.toObject(redisTemplate.opsForValue().get(key), clazz);
    }

    /**
     * get and set String
     */
    public String getAndSetValue(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * delete
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
