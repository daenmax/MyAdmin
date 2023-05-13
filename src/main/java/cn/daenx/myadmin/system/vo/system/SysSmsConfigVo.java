package cn.daenx.myadmin.system.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统短信配置
 */
@Data
@AllArgsConstructor
public class SysSmsConfigVo implements Serializable {

    private Config config;
    private SmsInfo aliyun;
    private SmsInfo tencent;

    @Data
    public static class Config {
        /**
         * 使用平台
         * aliyun=阿里云，tencent=腾讯云
         */
        private String type;
    }

    @Data
    public static class SmsInfo {
        /**
         * 是否启用该平台，true/false
         */
        private String enable;
        /**
         * 阿里云固定为：dysmsapi.aliyuncs.com
         * 腾讯云固定为：sms.tencentcloudapi.com
         */
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        /**
         * 签名
         */
        private String signName;
        /**
         * 应用ID，腾讯云专属
         */
        private String sdkAppId;
    }


}
