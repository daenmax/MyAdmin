package cn.daenx.myadmin.system.vo.system;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SysRegisterVo extends SysSubmitCaptchaVo {


    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 30, message = "账号长度需要在5到30个字符")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度需要在6到16个字符")
    private String password;
}
