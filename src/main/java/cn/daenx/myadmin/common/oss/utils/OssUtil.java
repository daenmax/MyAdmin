package cn.daenx.myadmin.common.oss.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.core.OssClient;
import cn.daenx.myadmin.common.oss.vo.OssProperties;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OssUtil {
    private static final Map<String, OssClient> CLIENT_CACHE = new ConcurrentHashMap<>();

    /**
     * 根据类型获取实例
     */
    public static OssClient getOssClient() {
        Object object = RedisUtil.getValue(RedisConstant.OSS);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未配置OSS配置信息");
        }
        OssProperties properties = JSON.parseObject(JSON.toJSONString(object), OssProperties.class);
        String name = properties.getName();
        OssClient client = CLIENT_CACHE.get(name);
        if (client == null) {
            CLIENT_CACHE.put(name, new OssClient(properties));
            log.info("创建OSS实例 key => {}", name);
            return CLIENT_CACHE.get(name);
        }
        // 配置不相同则重新构建
        if (!client.checkPropertiesSame(properties)) {
            CLIENT_CACHE.put(name, new OssClient(properties));
            log.info("重载OSS实例 key => {}", name);
            return CLIENT_CACHE.get(name);
        }
        return client;
    }
}
