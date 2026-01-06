package cn.daenx.framework.cache.redis.service.impl;

import cn.daenx.framework.cache.CacheService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Configuration
@ConditionalOnProperty(prefix = "cache", value = "type", havingValue = "redis")
public class RedisCacheServiceImpl implements CacheService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * getRedisTemplate
     *
     * @return 值
     */
    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * getType
     *
     * @return 值
     */
    @Override
    public String getType() {
        return "redis";
    }

    /**
     * getValue
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object getValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * setValue
     *
     * @param key      键
     * @param value    值
     * @param time     留空则永久有效
     * @param timeUnit 和上面保持一致
     * @return
     */
    @Override
    public boolean setValue(String key, Object value, Long time, TimeUnit timeUnit) {
        try {
            if (time != null) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * setValue
     * 永久
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public boolean setValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 左侧插入
     *
     * @param key
     * @param value
     * @return 列表插入后当前数量总数
     */
    @Override
    public Long leftPush(String key, Object value) {
        Long aLong = redisTemplate.opsForList().leftPush(key, value);
        return aLong;
    }

    /**
     * 右侧弹出
     *
     * @param key
     * @return
     */
    @Override
    public Object rightPop(String key) {
        return key == null ? null : redisTemplate.opsForList().rightPop(key);
    }


    /**
     * 左侧插入
     *
     * @param key
     * @param values
     * @return 列表插入后当前数量总数
     */
    @Override
    public <T> Long leftPushAll(String key, Collection<T> values) {
        Long aLong = redisTemplate.opsForList().leftPushAll(key, values);
        return aLong;
    }


    /**
     * 右侧弹出随后重新从左侧插入
     *
     * @param key
     * @return
     */
    @Override
    public Object rightPopAndLeftPush(String key) {
        return key == null ? null : redisTemplate.opsForList().rightPopAndLeftPush(key, key);
    }

    /**
     * 删除
     *
     * @param key
     */
    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param key 后面要跟上*号
     */
    @Override
    public void delBatch(String key) {
        Set<String> keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }


    /**
     * 获取列表
     *
     * @param key 后面要跟上*号
     * @return
     */
    @Override
    public List<Object> getList(String key) {
        Set keys = redisTemplate.keys(key);
        return keys != null ? new ArrayList<>(keys) : new ArrayList<>();
    }
}
