package cn.daenx.framework.cache.memory.data;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 内存缓存结构体
 *
 * @author DaenMax
 */
@Data
public class CacheObj<K, V> {

    /**
     * 缓存key
     */
    private K key;

    /**
     * 缓存value
     */
    private Object value;

    /**
     * 过期时长，-1=永不过期，单位毫秒
     */
    private long ttl;

    /**
     * 数据添加时间戳，单位毫秒
     */
    private long createTime;

    /**
     * 值类型：SINGLE=单个值，LIST=列表
     */
    private ValueType valueType;

    /**
     * 值类型枚举
     */
    public enum ValueType {
        SINGLE, LIST
    }

    /**
     * 构造单个值
     *
     * @param key
     * @param value
     * @param ttl   有效时长，单位毫秒
     */
    public CacheObj(K key, V value, long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
        this.createTime = System.currentTimeMillis();
        this.valueType = ValueType.SINGLE;
    }

    /**
     * 构造单个值
     *
     * @param key
     * @param value
     */
    public CacheObj(K key, V value) {
        this(key, value, -1);
    }

    /**
     * 构造列表值
     *
     * @param key
     * @param list
     * @param ttl  有效时长，单位毫秒
     */
    public CacheObj(K key, List<V> list, long ttl) {
        this.key = key;
        this.value = new LinkedList<>(list);
        this.ttl = ttl;
        this.createTime = System.currentTimeMillis();
        this.valueType = ValueType.LIST;
    }

    /**
     * 构造列表值
     *
     * @param key
     * @param list
     */
    public CacheObj(K key, List<V> list) {
        this(key, list, -1);
    }

    /**
     * 判断缓存是否过期
     *
     * @return
     */
    public boolean isExpired() {
        if (this.ttl > 0) {
            return (System.currentTimeMillis() - this.createTime) > this.ttl;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public V get() {
        if (valueType == ValueType.SINGLE) {
            return (V) this.value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<V> getList() {
        if (valueType == ValueType.LIST) {
            return (List<V>) this.value;
        }
        return null;
    }

    /**
     * 转换为列表（如果不存在则创建）
     */
    @SuppressWarnings("unchecked")
    public List<V> asList() {
        if (valueType == ValueType.LIST) {
            return (List<V>) this.value;
        } else if (valueType == ValueType.SINGLE) {
            // 单个值转换为列表
            LinkedList<V> list = new LinkedList<>();
            if (this.value != null) {
                list.add((V) this.value);
            }
            this.value = list;
            this.valueType = ValueType.LIST;
            return list;
        }
        return null;
    }
}
