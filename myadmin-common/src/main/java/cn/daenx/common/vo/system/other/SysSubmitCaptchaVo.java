package cn.daenx.common.vo.system.other;

import lombok.Data;

/**
 * 验证码相关参数
 */
@Data
public class SysSubmitCaptchaVo {
    /**
     * 通用验证码ID
     */
    private String uuid;


    /**
     * 图片验证码：验证码
     */
    private String code;


    /**
     * 滑块验证码：随机字符
     */
    private String randStr;
    /**
     * 滑块验证码：票据
     */
    private String ticket;
}
