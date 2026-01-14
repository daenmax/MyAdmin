package cn.daenx.modules.system.service;


import cn.daenx.framework.common.domain.vo.RouterVo;
import cn.daenx.modules.system.domain.dto.sysLogin.SysLoginDto;
import cn.daenx.modules.system.domain.dto.sysLogin.SysRegisterDto;
import cn.daenx.modules.system.domain.dto.sysLogin.SysSubmitCaptchaDto;

import java.util.List;
import java.util.Map;

public interface LoginService {
    /**
     * 创建验证码
     * 受系统参数配置影响
     *
     * @return
     */
    Map<String, Object> createCaptcha();

    /**
     * 校验验证码
     *
     * @param dto
     */
    void validatedCaptcha(SysSubmitCaptchaDto dto);

    /**
     * PC登录
     *
     * @param dto
     * @return
     */
    String login(SysLoginDto dto);

    /**
     * 通用注册接口
     * 只接受账号和密码
     * 手机号、邮箱、openid需要另外单独绑定
     *
     * @param dto
     */
    void register(SysRegisterDto dto);

    /**
     * 获取用户信息
     *
     * @return
     */
    Map<String, Object> getInfo();

    /**
     * 根据用户获取菜单树
     *
     * @return
     */

    List<RouterVo> getRouters();

    /**
     * 退出登录
     */
    void logout();

    /**
     * 获取邮箱验证码
     *
     * @param dto
     * @return
     */
    Map<String, Object> getEmailValidCode(SysLoginDto dto);

    /**
     * 获取手机验证码
     *
     * @param dto
     * @return
     */
    Map<String, Object> getPhoneValidCode(SysLoginDto dto);
}
