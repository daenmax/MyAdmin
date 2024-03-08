package cn.daenx.framework.oss.utils;

import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.oss.core.OssClient;
import cn.daenx.framework.oss.vo.OssProperties;
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
    public static synchronized OssClient getOssClientByOssConfigId(String ossConfigId) {
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
    public static synchronized OssClient getOssClientByOssProperties(OssProperties properties) {
        return handle(properties);
    }

    private static OssClient handle(OssProperties properties) {
        String id = properties.getId();
        OssClient client = clientMap.get(id);
        if (client == null) {
            clientMap.put(id, new OssClient(properties));
            log.info("创建OSS实例 key => {}，name => {}", id, properties.getName());
            return clientMap.get(id);
        }
        // 配置不相同则重新构建
        if (!client.checkPropertiesSame(properties)) {
            clientMap.put(id, new OssClient(properties));
            log.info("重载OSS实例 key => {}，name => {}", id, properties.getName());
            return clientMap.get(id);
        }
        return client;
    }

    /**
     * 删除缓存
     *
     * @param key ossConfigId
     */
    public static void removeKey(String key) {
        clientMap.remove(key);
    }

    /**
     * 翻译错误信息
     *
     * @param msg
     * @return
     */
    public static String transErrMsg(String msg) {
        if (msg.contains("The specified key does not exist")) {
            return "桶中该文件对象已不存在";
        }
        return msg;
    }
}
