package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;

public class LoginUtil {
    public static String USER = "user";

    public static void login(SysLoginUserVo sysLoginUserVo, DeviceType deviceType) {
        SaHolder.getStorage().set(USER, sysLoginUserVo);
        StpUtil.login(sysLoginUserVo.getUsername(), deviceType.getCode());
        StpUtil.getTokenSession().set(USER, sysLoginUserVo);

    }


    /**
     * 获取用户
     */
    public static SysLoginUserVo getLoginUser() {
        SysLoginUserVo loginUser = (SysLoginUserVo) SaHolder.getStorage().get(USER);
        if (loginUser != null) {
            return loginUser;
        }
        loginUser = (SysLoginUserVo) StpUtil.getTokenSession().get(USER);
        SaHolder.getStorage().set(USER, loginUser);
        return loginUser;
    }
}
