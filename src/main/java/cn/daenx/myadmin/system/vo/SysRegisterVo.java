package cn.daenx.myadmin.system.vo;

import lombok.Data;

@Data
public class SysRegisterVo{

    /**
     * 验证码
     */
    private String code;
    /**
     * 验证码ID
     */
    private String uuid;
    private String username;
    private String password;
}
