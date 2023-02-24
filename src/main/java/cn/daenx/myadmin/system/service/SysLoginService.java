package cn.daenx.myadmin.system.service;


import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;

public interface SysLoginService {

    /**
     * PC登录
     *
     * @param vo
     * @return
     */
    String login(SysLoginVo vo);

    /**
     * 通用注册接口
     * 只接受账号和密码
     * 手机号、邮箱、openid需要另外单独绑定
     *
     * @param vo
     */
    void register(SysRegisterVo vo);
}
