package cn.daenx.framework.common.domain.vo.system.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 系统短信配置
 */
@Data
@AllArgsConstructor
public class SysSmsConfigVo implements Serializable {

    private Config config;
    private Map<String, Map<String, String>> platform;

    @Data
    public static class Config {
        /**
         * 使用平台
         * 例如：aliyun=阿里云，tencent=腾讯云等
         */
        private String type;
    }

}
