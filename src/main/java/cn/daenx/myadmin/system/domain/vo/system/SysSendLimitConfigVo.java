package cn.daenx.myadmin.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统发送验证码时的限制配置
 * 如果不配置将没有限制
 */
@Data
@AllArgsConstructor
public class SysSendLimitConfigVo implements Serializable {

    private Config email;
    private Config sms;

    /**
     * 配置
     */
    @Data
    public static class Config {
        /**
         * 限制方式
         * 0=用户ID，1=IP
         */
        private Integer limitType;

        /**
         * 每次发送后需要等待多久才可以继续发
         * 单位秒，-1=不限制
         */
        private Integer needWait;

        /**
         * 每天最多发几次，0点刷新
         * -1=不限制
         */
        private Integer dayMax;

        /**
         * 验证码有效期，单位秒
         */
        private Integer keepLive;
    }
}
