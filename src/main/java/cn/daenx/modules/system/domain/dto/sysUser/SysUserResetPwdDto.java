package cn.daenx.modules.system.domain.dto.sysUser;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SysUserResetPwdDto {
    @NotBlank(message = "用户id不能为空")
    private String id;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度需要在6到16个字符")
    private String password;
}
