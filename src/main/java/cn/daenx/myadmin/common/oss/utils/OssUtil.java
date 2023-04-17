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

/**
 * OSS存储工具类
 */
@Slf4j
public class OssUtil {
    /**
     * 缓存
     */
    private static final Map<String, OssClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 获取当前正在使用的OSS配置实例
     */
    public static OssClient getOssClient() {
        Object object = RedisUtil.getValue(RedisConstant.OSS_USE);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未配置OSS配置信息，请联系管理员");
        }
        OssProperties properties = JSON.parseObject(JSON.toJSONString(object), OssProperties.class);
        return handle(properties);
    }

    /**
     * 获取指定的OSS配置实例
     */
    public static OssClient getOssClientByOssConfigId(String ossConfigId) {
        Object object = RedisUtil.getValue(RedisConstant.OSS + ossConfigId);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未找到OSS配置信息，请联系管理员");
        }
        OssProperties properties = JSON.parseObject(JSON.toJSONString(object), OssProperties.class);
        return handle(properties);
    }

    /**
     * 获取指定的OSS配置实例
     */
    public static OssClient getOssClientByOssProperties(OssProperties properties) {
        return handle(properties);
    }

    private static OssClient handle(OssProperties properties) {
        String name = properties.getName();
        OssClient client = clientMap.get(name);
        if (client == null) {
            clientMap.put(name, new OssClient(properties));
            log.info("创建OSS实例 key => {}", name);
            return clientMap.get(name);
        }
        // 配置不相同则重新构建
        if (!client.checkPropertiesSame(properties)) {
            clientMap.put(name, new OssClient(properties));
            log.info("重载OSS实例 key => {}", name);
            return clientMap.get(name);
        }
        return client;
    }
}
