package com.redis.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldSaveToCache() {
        String key = "123";
        String value = "Value";

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        customerService.saveToCache(key, value, 60);

        verify(valueOperations, times(1))
                .set(key, value, 60, TimeUnit.SECONDS);
    }

    @Test
    void shouldGetFromCache() {
        String key = "123";
        String expectedValue = "Value";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(expectedValue);

        Object result = customerService.getFromCache(key);

        verify(valueOperations, times(1)).get(key);
        assertEquals(expectedValue, result);
    }

    @Test
    void shouldClearCache() {
        String key = "123";

        customerService.clearCache(key);

        verify(redisTemplate, times(1)).delete(key);
    }
}
