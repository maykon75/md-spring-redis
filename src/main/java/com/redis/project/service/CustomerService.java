package com.redis.project.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {

    private final RedisTemplate<String, Object> redisTemplate;

    public CustomerService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToCache(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public Object getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void clearCache(String key) {
        redisTemplate.delete(key);
    }

}
