package cn.daenx.myadmin.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统短信模板ID配置
 */
@Data
@AllArgsConstructor
public class SysSmsTemplateConfigVo implements Serializable {

    /**
     * 注册时
     */
    private Config register;

    /**
     * 登录时
     */
    private Config login;

    /**
     * 绑定手机时
     */
    private Config bindPhone;

    /**
     * 找回密码时
     */
    private Config findPassword;

    /**
     * 定时任务异常时
     */
    private Config jobError;

    @Data
    public static class Config {

        /**
         * 模板ID
         */
        private String templateId;

        /**
         * 变量名
         */
        private String variable;

        /**
         * 当前变量的最大长度，超出后会截断
         */
        private Integer length;
    }
}
