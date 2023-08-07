package cn.daenx.system.service;


import cn.daenx.framework.common.vo.system.config.SysCaptchaConfigVo;
import cn.daenx.system.domain.vo.SysSubmitCaptchaVo;

import java.util.HashMap;

public interface CaptchaService {
    /**
     * 创建验证码
     * 受系统参数配置影响
     *
     * @return
     */
    HashMap<String, Object> createCaptcha();

    /**
     * 创建图片验证码
     *
     * @param sysCaptchaConfigVo
     * @return
     */
    HashMap<String, Object> createCaptchaImgToBase64(SysCaptchaConfigVo sysCaptchaConfigVo);

    /**
     * 创建腾讯滑块验证码
     *
     * @param sysCaptchaConfigVo
     * @return
     */
    HashMap<String, Object> createCaptchaSlider(SysCaptchaConfigVo sysCaptchaConfigVo);

    /**
     * 校验验证码
     *
     * @param vo
     */
    void validatedCaptcha(SysSubmitCaptchaVo vo);
}
