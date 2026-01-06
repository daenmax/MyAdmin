package cn.daenx.framework.cache.utils;

import cn.daenx.framework.cache.CacheService;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author DaenMax
 */
public class CacheUtil {

//    private static CacheService cacheService;

    private static final CacheService cacheService = SpringUtil.getBean(CacheService.class);

    /**
     * getType
     *
     * @return 值
     */
    public static String getType() {
        return cacheService.getType();
    }


    /**
     * getValue
     *
     * @param key 键
     * @return 值
     */
    public static Object getValue(String key) {
        return key == null ? null : cacheService.getValue(key);
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
        cacheService.setValue(key, value, time, timeUnit);
        return true;
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
        cacheService.setValue(key, value);
        return true;
    }

    /**
     * 左侧插入
     *
     * @param key
     * @param value
     * @return 列表插入后当前数量总数
     */
    public static Long leftPush(String key, Object value) {
        Long aLong = cacheService.leftPush(key, value);
        return aLong;
    }

    /**
     * 右侧弹出
     *
     * @param key
     * @return
     */
    public static Object rightPop(String key) {
        return key == null ? null : cacheService.rightPop(key);
    }


    /**
     * 左侧插入
     *
     * @param key
     * @param values
     * @return 列表插入后当前数量总数
     */
    public static <T> Long leftPushAll(String key, Collection<T> values) {
        Long aLong = cacheService.leftPushAll(key, values);
        return aLong;
    }


    /**
     * 右侧弹出随后重新从左侧插入
     *
     * @param key
     * @return
     */
    public static Object rightPopAndLeftPush(String key) {
        return cacheService.rightPopAndLeftPush(key);
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void del(String key) {
        cacheService.del(key);
    }

    /**
     * 批量删除
     *
     * @param key 后面要跟上*号
     */
    public static void delBatch(String key) {
        cacheService.delBatch(key);
    }


    /**
     * 获取列表
     *
     * @param key 后面要跟上*号
     * @return
     */
    public static List<Object> getList(String key) {
        return cacheService.getList(key);
    }

    public static RedisTemplate<String, Object> getRedisTemplate() {
        return cacheService.getRedisTemplate();
    }
}
