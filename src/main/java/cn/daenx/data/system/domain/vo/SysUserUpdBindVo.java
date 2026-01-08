package cn.daenx.data.system.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserUpdBindVo extends SysSubmitCaptchaVo {
    /**
     * 提交时才需要用到，验证码
     */
    private String validCode;

    private String email;
    private String phone;

}
