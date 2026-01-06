package cn.daenx.framework.cache.memory.data;

import cn.daenx.framework.common.utils.MyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 内存缓存实现
 *
 * @author DaenMax
 */
@Slf4j
public class TimeCache<K, V> {

    private final Map<K, CacheObj<K, V>> MAP = new ConcurrentHashMap<>();

    /**
     * 容量，-1=不限量
     */
    private final int capacity;

    /**
     * 构造
     *
     * @param capacity       容量 -1(0) 表示不限量
     * @param period         缓存清理器间隔时间
     * @param periodTimeUnit 缓存清理器间隔时间单位
     */
    public TimeCache(int capacity, long period, TimeUnit periodTimeUnit) {
        this.capacity = capacity;
        if (period > 0) {
            ScheduledFuture<?> schedule = MemoryCleanTimer.getInstance().schedule(this::cleanExpire, period, periodTimeUnit);
            log.info(MyUtil.getLogPrex() + "缓存策略：{}", "memory");
        }
    }

    private void cleanExpire() {
        for (Map.Entry<K, CacheObj<K, V>> cacheObjEntry : MAP.entrySet()) {
            CacheObj<K, V> value = cacheObjEntry.getValue();
            if (value != null && value.isExpired()) {
                remove(cacheObjEntry.getKey());
            }
        }
    }

    private CacheObj<K, V> getCacheObj(K key) {
        CacheObj<K, V> kvCacheObj = MAP.get(key);
        if (kvCacheObj == null) {
            return null;
        }
        if (kvCacheObj.isExpired()) {
            MAP.remove(key);
            return null;
        }
        return kvCacheObj;
    }

    public void put(K key, V value, Long time, TimeUnit timeUnit) {
        checkCapacity();
        CacheObj<K, V> obj = new CacheObj<>(key, value, timeUnit.toMillis(time));
        MAP.put(key, obj);
    }

    public void put(K key, V value) {
        checkCapacity();
        CacheObj<K, V> obj = new CacheObj<>(key, value);
        MAP.put(key, obj);
    }

    /**
     * 将列表放入缓存
     */
    public void putList(K key, List<V> list, Long time, TimeUnit timeUnit) {
        checkCapacity();
        CacheObj<K, V> obj = new CacheObj<>(key, list, timeUnit.toMillis(time));
        MAP.put(key, obj);
    }

    /**
     * 将列表放入缓存
     */
    public void putList(K key, List<V> list) {
        checkCapacity();
        CacheObj<K, V> obj = new CacheObj<>(key, list);
        MAP.put(key, obj);
    }

    public V get(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null) {
            return cacheObj.get();
        }
        return null;
    }

    /**
     * 获取列表的大小
     */
    public long listSize(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null) {
            List<V> list = cacheObj.getList();
            if (list != null) {
                return list.size();
            }
        }
        return 0;
    }

    /**
     * 从左端推入一个元素
     * 如果key不存在，会创建新的列表
     */
    public int leftPush(K key, V value) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        if (list instanceof LinkedList) {
            ((LinkedList<V>) list).addFirst(value);
        } else {
            LinkedList<V> linkedList = new LinkedList<>(list);
            linkedList.addFirst(value);
            cacheObj.setValue(linkedList);
        }
        return list.size() + 1;
    }

    /**
     * 从左端推入多个元素
     */
    public int leftPushAll(K key, V... values) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        if (list instanceof LinkedList) {
            LinkedList<V> linkedList = (LinkedList<V>) list;
            for (int i = values.length - 1; i >= 0; i--) {
                linkedList.addFirst(values[i]);
            }
            return linkedList.size();
        } else {
            LinkedList<V> linkedList = new LinkedList<>(list);
            for (int i = values.length - 1; i >= 0; i--) {
                linkedList.addFirst(values[i]);
            }
            cacheObj.setValue(linkedList);
            return linkedList.size();
        }
    }

    /**
     * 从左端推入集合的所有元素
     */
    public void leftPushAll(K key, Collection<V> values) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        if (list instanceof LinkedList) {
            LinkedList<V> linkedList = (LinkedList<V>) list;
            for (V value : values) {
                linkedList.addFirst(value);
            }
        } else {
            LinkedList<V> linkedList = new LinkedList<>(list);
            for (V value : values) {
                linkedList.addFirst(value);
            }
            cacheObj.setValue(linkedList);
        }
    }

    /**
     * 从右端弹出一个元素
     */
    public V rightPop(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null) {
            List<V> list = cacheObj.getList();
            if (list != null && !list.isEmpty()) {
                if (list instanceof LinkedList) {
                    return ((LinkedList<V>) list).pollLast();
                } else {
                    V value = list.remove(list.size() - 1);
                    if (list.isEmpty()) {
                        // 如果列表为空，可以选择删除该key或保留空列表
                        // 这里选择保留空列表
                        cacheObj.setValue(new LinkedList<V>());
                    }
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 从右端弹出并推入到另一个列表的左端
     */
    public V rightPopAndLeftPush(K sourceKey, K destinationKey) {
        checkCapacity();
        // 先从源列表右端弹出
        V value = rightPop(sourceKey);
        if (value != null) {
            // 再推入到目标列表左端
            leftPush(destinationKey, value);
        }
        return value;
    }

    /**
     * 从左端弹出一个元素
     */
    public V leftPop(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null) {
            List<V> list = cacheObj.getList();
            if (list != null && !list.isEmpty()) {
                if (list instanceof LinkedList) {
                    return ((LinkedList<V>) list).pollFirst();
                } else {
                    V value = list.remove(0);
                    if (list.isEmpty()) {
                        cacheObj.setValue(new LinkedList<V>());
                    }
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 从右端推入一个元素
     */
    public void rightPush(K key, V value) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        list.add(value);
    }

    /**
     * 从右端推入多个元素
     */
    public void rightPushAll(K key, V... values) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        Collections.addAll(list, values);
    }

    /**
     * 从右端推入集合的所有元素
     */
    public void rightPushAll(K key, Collection<V> values) {
        checkCapacity();
        CacheObj<K, V> cacheObj = getOrCreateList(key);
        List<V> list = cacheObj.asList();
        list.addAll(values);
    }

    /**
     * 获取列表范围元素
     */
    public List<V> listRange(K key, long start, long end) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null) {
            List<V> list = cacheObj.getList();
            if (list != null && !list.isEmpty()) {
                int size = list.size();
                int startIndex = (int) (start >= 0 ? start : size + start);
                int endIndex = (int) (end >= 0 ? end : size + end);

                startIndex = Math.max(0, Math.min(startIndex, size - 1));
                endIndex = Math.max(0, Math.min(endIndex, size - 1));

                if (startIndex > endIndex) {
                    return new ArrayList<>();
                }

                return new ArrayList<>(list.subList(startIndex, endIndex + 1));
            }
        }
        return new ArrayList<>();
    }

    /**
     * 获取或创建列表
     */
    private CacheObj<K, V> getOrCreateList(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj == null) {
            cacheObj = new CacheObj<>(key, new LinkedList<V>());
            MAP.put(key, cacheObj);
        }
        return cacheObj;
    }

    /**
     * 检查容量
     */
    private void checkCapacity() {
        if (capacity > 0 && MAP.size() >= capacity) {
            throw new RuntimeException("超出最大限制:" + capacity);
        }
    }

    /**
     * 获取剩余过期时长
     */
    public long getExpire(K key) {
        CacheObj<K, V> cacheObj = getCacheObj(key);
        if (cacheObj != null && !cacheObj.isExpired()) {
            if (cacheObj.getTtl() > 0) {
                long lastGet = cacheObj.getCreateTime();
                return lastGet + cacheObj.getTtl() - System.currentTimeMillis();
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    /**
     * 判断缓存中是否存在某个key
     */
    public Boolean exists(K key) {
        return MAP.containsKey(key);
    }

    /**
     * 删除某个key
     */
    public Boolean remove(K key) {
        CacheObj<K, V> cacheObj = MAP.remove(key);
        return cacheObj != null;
    }


    /**
     * 批量删除
     *
     * @param pattern key模式，支持*通配符
     *                示例： "user:*" - 删除所有以user:开头的key
     *                示例： "*:cache" - 删除所有以:cache结尾的key
     *                示例： "*temp*" - 删除所有包含temp的key
     * @return 删除的key数量
     */
    public long delBatch(String pattern) {
        // 获取所有匹配的key
        Set<K> keys = keys(pattern);

        // 删除匹配的key
        long deletedCount = 0;
        for (K key : keys) {
            if (remove(key)) {
                deletedCount++;
            }
        }

        return deletedCount;
    }

    /**
     * 获取所有匹配的key（仅key，不包含值）
     *
     * @param pattern key模式，支持*通配符
     *                示例： "user:*" - 获取所有以user:开头的key
     *                示例： "*:cache" - 获取所有以:cache结尾的key
     *                示例： "*temp*" - 获取所有包含temp的key
     * @return 匹配的key集合
     */
    public Set<K> keys(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return new HashSet<>();
        }

        Set<K> matchedKeys = new HashSet<>();

        // 如果没有通配符，直接检查是否存在
        if (!pattern.contains("*")) {
            if (MAP.containsKey(pattern)) {
                // 由于泛型K可能是String，这里需要类型转换
                matchedKeys.add((K) pattern);
            }
            return matchedKeys;
        }

        // 将模式转换为正则表达式
        String regex = pattern
                .replace(".", "\\.")
                .replace("*", ".*");

        // 遍历所有key进行匹配
        for (K key : MAP.keySet()) {
            if (key != null && key.toString().matches(regex)) {
                matchedKeys.add(key);
            }
        }

        return matchedKeys;
    }

    /**
     * 获取列表
     */
    public List<Object> getList(String key) {
        Set<K> keys = keys(key);
        List<Object> list = new ArrayList<>();
        for (K k : keys) {
            CacheObj<K, V> cacheObj = getCacheObj(k);
            list.add(cacheObj.getValue());
        }
        return list;
    }

}
