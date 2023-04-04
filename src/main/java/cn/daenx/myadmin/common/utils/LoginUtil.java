package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;

import java.util.List;

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
     * 退出登录
     */
    public static void logoutByToken(String token) {
        StpUtil.logoutByTokenValue(token);
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
     * 获取登录用户
     */
    public static SysLoginUserVo getLoginUserByToken(String token) {
        return (SysLoginUserVo) StpUtil.getTokenSessionByToken(token).get(LOGIN_KEY);
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

    public static List<String> getLoginTokenList(){
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        return keys;
    }
    public static long getTokenActivityTimeoutByToken(String token){
        return StpUtil.stpLogic.getTokenActivityTimeoutByToken(token);
    }
}
