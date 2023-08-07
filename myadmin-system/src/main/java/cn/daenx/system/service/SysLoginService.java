package cn.daenx.system.service;


import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.common.vo.RouterVo;
import cn.daenx.system.domain.vo.SysLoginVo;
import cn.daenx.system.domain.vo.SysRegisterVo;

import java.util.List;
import java.util.Map;

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
     * @param vo
     * @return
     */
    Result getEmailValidCode(SysLoginVo vo);

    /**
     * 获取手机验证码
     *
     * @param vo
     * @return
     */
    Result getPhoneValidCode(SysLoginVo vo);
}
