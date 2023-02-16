package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;

@Service
public class SysLoginServiceImpl implements SysLoginService {

    /**
     * 用户登录
     *
     * @param vo
     * @return
     */
    @Override
    public String login(SysLoginVo vo) {
        SysUser sysUser = new SysUser();
        sysUser.setId("123");
        StpUtil.getTokenSession().set("loginuser",sysUser);
        StpUtil.login(vo.getUsername(), DeviceType.PC.getCode());
        System.out.println(StpUtil.getLoginId());
        System.out.println(StpUtil.getLoginDevice());
        return StpUtil.getTokenValue();
    }
}
