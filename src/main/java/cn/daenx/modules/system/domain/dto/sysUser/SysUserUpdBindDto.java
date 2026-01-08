package cn.daenx.modules.system.domain.dto.sysUser;

import cn.daenx.modules.system.domain.dto.sysLogin.SysSubmitCaptchaDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserUpdBindDto extends SysSubmitCaptchaDto {
    /**
     * 提交时才需要用到，验证码
     */
    private String validCode;

    private String email;
    private String phone;

}
