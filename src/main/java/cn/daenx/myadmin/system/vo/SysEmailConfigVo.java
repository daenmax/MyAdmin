package cn.daenx.myadmin.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统邮箱配置
 */
@Data
@AllArgsConstructor
public class SysEmailConfigVo implements Serializable {

    private Config config;
    private List<Email> emails;

    @Data
    public static class Config {
        /**
         * 邮箱使用模式
         * 0=轮询，1=完全随机，2=权重随机，默认0
         */
        private String mode;
    }

    @Data
    public static class Email {
        /**
         * 是否启用该邮箱，true/false
         */
        private String enable;
        /**
         * 域名，例如：smtp.qq.com
         */
        private String host;
        /**
         * 端口，例如465、587
         */
        private Integer port;
        /**
         * 编码，例如：UTF-8
         */
        private String encode;
        /**
         * 协议，例如：smtp
         */
        private String protocol;
        /**
         * 账号
         * 1330166564@qq.com
         */
        private String email;
        /**
         * 来源账号
         * 1330166564@qq.com 或者 MyAdmin<1330166564@qq.com>
         */
        private String from;
        /**
         * 密码或者smtp授权码
         */
        private String password;
        /**
         * 超时时间，单位毫秒
         */
        private String timeout;
        /**
         * 是否认证，true/false
         */
        private String auth;
        /**
         * SSL工厂
         */
        private String socketFactoryClass;
        /**
         * 随机权重，可空
         */
        private String weight;
    }


}
