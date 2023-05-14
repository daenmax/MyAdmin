package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.system.vo.system.SysSubmitCaptchaVo;
import lombok.Data;

@Data
public class SysUserUpdBindVo extends SysSubmitCaptchaVo {
    /**
     * 提交时才需要用到，验证码
     */
    private String validCode;

    private String email;
    private String phone;

}
