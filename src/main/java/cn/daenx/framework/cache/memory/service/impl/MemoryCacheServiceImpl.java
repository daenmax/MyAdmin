package cn.daenx.framework.cache.memory.service.impl;

import cn.daenx.framework.cache.CacheService;
import cn.daenx.framework.cache.memory.data.TimeCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(prefix = "cache", value = "type", havingValue = "memory")
public class MemoryCacheServiceImpl implements CacheService {

    private static final TimeCache<String, Object> TIMED_CACHE = new TimeCache<String, Object>(Integer.MAX_VALUE, 1, TimeUnit.SECONDS);

    /**
     * getType
     *
     * @return 值
     */
    @Override
    public String getType() {
        return "memory";
    }

    /**
     * getValue
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object getValue(String key) {
        return TIMED_CACHE.get(key);
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
        TIMED_CACHE.put(key, value, time, timeUnit);
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
    @Override
    public boolean setValue(String key, Object value) {
        TIMED_CACHE.put(key, value);
        return true;
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
        return Long.valueOf(TIMED_CACHE.leftPush(key, value));
    }

    /**
     * 右侧弹出
     *
     * @param key
     * @return
     */
    @Override
    public Object rightPop(String key) {
        return TIMED_CACHE.rightPop(key);
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
        return Long.valueOf(TIMED_CACHE.leftPushAll(key, values));
    }

    /**
     * 右侧弹出随后重新从左侧插入
     *
     * @param key
     * @return
     */
    @Override
    public Object rightPopAndLeftPush(String key) {
        return TIMED_CACHE.rightPopAndLeftPush(key, key);
    }

    /**
     * 删除
     *
     * @param key
     */
    @Override
    public void del(String key) {
        TIMED_CACHE.remove(key);
    }

    /**
     * 批量删除
     *
     * @param key 后面要跟上*号
     */
    @Override
    public void delBatch(String key) {
        TIMED_CACHE.delBatch(key);
    }

    /**
     * 获取列表
     *
     * @param key 后面要跟上*号
     * @return
     */
    @Override
    public List<Object> getList(String key) {
        return TIMED_CACHE.getList(key);
    }
}
