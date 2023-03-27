package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;

public class LoginUtil {
    public static String LOGIN_KEY = "system";

    /**
     * 设置登录
     *
     * @param sysLoginUserVo
     * @param deviceType
     */
    public static void login(SysLoginUserVo sysLoginUserVo, DeviceType deviceType) {
        SaHolder.getStorage().set(LOGIN_KEY, sysLoginUserVo);
        StpUtil.login(sysLoginUserVo.getUsername(), deviceType.getCode());
        StpUtil.getTokenSession().set(LOGIN_KEY, sysLoginUserVo);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        StpUtil.logout();
    }

    /**
     * 退出登录
     */
    public static void logout(String username) {
        StpUtil.logout(username);
    }

    /**
     * 获取登录用户
     */
    public static SysLoginUserVo getLoginUser() {
        SysLoginUserVo loginUser = (SysLoginUserVo) SaHolder.getStorage().get(LOGIN_KEY);
        if (loginUser != null) {
            return loginUser;
        }
        loginUser = (SysLoginUserVo) StpUtil.getTokenSession().get(LOGIN_KEY);
        SaHolder.getStorage().set(LOGIN_KEY, loginUser);
        return loginUser;
    }

    /**
     * 获取登录用户ID
     */
    public static String getLoginUserId() {
        return getLoginUser().getId();
    }

    public static boolean isAdmin(String userId) {
        return SystemConstant.IS_ADMIN_ID.equals(userId);
    }

    public static boolean isAdmin() {
        return SystemConstant.IS_ADMIN_ID.equals(getLoginUserId());
    }
}
