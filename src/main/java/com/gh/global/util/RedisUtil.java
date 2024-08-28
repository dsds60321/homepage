package com.gh.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForValue().get(key));
    }



    // TODO: 이전 만든 내용 삭제 예정


    public void set(String key, Object value, boolean isExpiredDay) {
        if (isExpiredDay) {
//            redisTemplate.opsForValue().set(key, value, DateUtil.getSecondsUntilMidnight());
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }



//    public <T> T get(String key, Class<T> clazz) {
//        Object value = redisTemplate.opsForValue().get(key);
//
//        if (value == null) {
//            return null;
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        return objectMapper.convertValue(value, clazz);
//    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key).booleanValue();
    }

    public Set<String> findByKeysRedisPattern(RedisTemplate<String, ?> redisTemplate, String pattern) {
        Set<String> keys = new HashSet<>();
        redisTemplate.execute((RedisConnection connection) -> {
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build());
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return null;
        });
        return keys;
    }
}
