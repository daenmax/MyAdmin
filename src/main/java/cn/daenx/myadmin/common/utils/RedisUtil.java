package cn.daenx.myadmin.common.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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


}
