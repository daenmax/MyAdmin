package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysUserUpdInfoVo {
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别，0=女，1=男，2=未知
     */
    private String sex;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 个性签名
     */
    private String userSign;

}
