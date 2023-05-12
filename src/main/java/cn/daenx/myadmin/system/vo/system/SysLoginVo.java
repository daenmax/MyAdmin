package cn.daenx.myadmin.system.vo.system;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录
 */
@Data
public class SysLoginVo {

    /**
     * 验证码
     */
    private String code;
    /**
     * 验证码ID
     */
    private String uuid;
    /**
     * 登录方式
     */
    @NotBlank(message = "loginType不能为空")
    private String loginType;


    /**
     * 用账号密码登录时
     */
    private String username;
    private String password;

    /**
     * 用短信验证码登录时
     */
    private String phone;

    /**
     * 用邮箱验证码登录时
     */
    private String email;

    /**
     * 用微信扫码或微信小程序扫码登录时（暂未实现）
     */
    private String wxCode;

    /**
     * 用开放API登录时
     */
    private String apiKey;
}
