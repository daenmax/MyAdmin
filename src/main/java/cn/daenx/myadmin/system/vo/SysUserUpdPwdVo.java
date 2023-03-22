package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysUserUpdPwdVo {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
