package cn.daenx.common.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author DaenMax
 */
@Component
public class RedisUtil {

    private static RedisTemplate redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }


    public static RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * getValue
     *
     * @param key 键
     * @return 值
     */
    public static Object getValue(String key) {
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
    public static boolean setValue(String key, Object value, Long time, TimeUnit timeUnit) {
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
    public static boolean setValue(String key, Object value) {
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
    public static Long leftPush(String key, Object value) {
        Long aLong = redisTemplate.opsForList().leftPush(key, value);
        return aLong;
    }

    /**
     * 右侧弹出
     *
     * @param key
     * @return
     */
    public static Object rightPop(String key) {
        return key == null ? null : redisTemplate.opsForList().rightPop(key);
    }


    /**
     * 左侧插入
     *
     * @param key
     * @param values
     * @return 列表插入后当前数量总数
     */
    public static <T> Long leftPushAll(String key, Collection<T> values) {
        Long aLong = redisTemplate.opsForList().leftPushAll(key, values);
        return aLong;
    }


    /**
     * 右侧弹出随后重新从左侧插入
     *
     * @param key
     * @return
     */
    public static Object rightPopAndLeftPush(String key) {
        return key == null ? null : redisTemplate.opsForList().rightPopAndLeftPush(key, key);
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param key 后面要跟上*号
     */
    public static void delBatch(String key) {
        Set<String> keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }


    /**
     * 获取列表
     *
     * @param key 后面要跟上*号
     * @return
     */
    public static Collection<String> getList(String key) {
        Set keys = redisTemplate.keys(key);
        return keys;
    }
}
