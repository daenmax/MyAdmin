package cn.daenx.myadmin.system.service;


import cn.daenx.myadmin.system.vo.SysLoginVo;

public interface SysLoginService {

    /**
     * 用户登录
     *
     * @param vo
     * @return
     */
    String login(SysLoginVo vo);
}
