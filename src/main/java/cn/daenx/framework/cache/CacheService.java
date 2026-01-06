package cn.daenx.framework.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface CacheService {

    /**
     * getRedisTemplate
     *
     * @return 值
     */
    default RedisTemplate<String, Object> getRedisTemplate() {
        throw new RuntimeException("未实现[getRedisTemplate]方法");
    }

    /**
     * getType
     *
     * @return 值
     */
    default String getType() {
        throw new RuntimeException("未实现[getType]方法");
    }

    /**
     * getValue
     *
     * @param key 键
     * @return 值
     */
    default Object getValue(String key) {
        throw new RuntimeException("未实现[getValue]方法");
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
    default boolean setValue(String key, Object value, Long time, TimeUnit timeUnit) {
        throw new RuntimeException("未实现[setValue]方法");
    }

    /**
     * setValue
     * 永久
     *
     * @param key   键
     * @param value 值
     * @return
     */
    default boolean setValue(String key, Object value) {
        throw new RuntimeException("未实现[setValue]方法");
    }

    /**
     * 左侧插入
     *
     * @param key
     * @param value
     * @return 列表插入后当前数量总数
     */
    default Long leftPush(String key, Object value) {
        throw new RuntimeException("未实现[leftPush]方法");
    }

    /**
     * 右侧弹出
     *
     * @param key
     * @return
     */
    default Object rightPop(String key) {
        throw new RuntimeException("未实现[rightPop]方法");
    }


    /**
     * 左侧插入
     *
     * @param key
     * @param values
     * @return 列表插入后当前数量总数
     */
    default <T> Long leftPushAll(String key, Collection<T> values) {
        throw new RuntimeException("未实现[leftPushAll]方法");
    }


    /**
     * 右侧弹出随后重新从左侧插入
     *
     * @param key
     * @return
     */
    default Object rightPopAndLeftPush(String key) {
        throw new RuntimeException("未实现[rightPopAndLeftPush]方法");
    }

    /**
     * 删除
     *
     * @param key
     */
    default void del(String key) {
        throw new RuntimeException("未实现[del]方法");
    }

    /**
     * 批量删除
     *
     * @param key 后面要跟上*号
     */
    default void delBatch(String key) {
        throw new RuntimeException("未实现[delBatch]方法");
    }


    /**
     * 获取列表
     *
     * @param key 后面要跟上*号
     * @return
     */
    default List<Object> getList(String key) {
        throw new RuntimeException("未实现[getList]方法");
    }
}
