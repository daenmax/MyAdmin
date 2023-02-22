package cn.daenx.myadmin.system.service;


import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;

public interface SysLoginService {

    /**
     * 用户登录
     *
     * @param vo
     * @return
     */
    String login(SysLoginVo vo);

    /**
     * 用户注册
     *
     * @param vo
     */
    void register(SysRegisterVo vo);
}
