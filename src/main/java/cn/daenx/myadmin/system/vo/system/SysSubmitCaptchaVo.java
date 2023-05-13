package cn.daenx.myadmin.system.vo.system;

import lombok.Data;

/**
 * 验证码相关参数
 */
@Data
public class SysSubmitCaptchaVo {

    /**
     * 图片验证码：验证码
     */
    private String code;
    /**
     * 图片验证码：验证码ID
     */
    private String uuid;

    /**
     * 滑块验证码：随机字符
     */
    private String randStr;
    /**
     * 滑块验证码：票据
     */
    private String ticket;
}
