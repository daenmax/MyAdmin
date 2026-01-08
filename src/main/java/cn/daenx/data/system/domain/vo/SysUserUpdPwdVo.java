package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SysUserUpdPwdVo {
    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度需要在6到16个字符")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度需要在6到16个字符")
    private String newPassword;
}
