package cn.daenx.framework.satoken.utils;

import cn.daenx.framework.common.constant.enums.DeviceType;
import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginUtil {

    private static Long timeOut;

    @Value("${sa-token.timeout}")
    public void setTimeOut(Long timeOut) {
        LoginUtil.timeOut = timeOut;
    }

    public static String LOGIN_KEY = "system";

    /**
     * 登录
     *
     * @param sysLoginUserVo
     * @param deviceType
     */
    public static void login(SysLoginUserVo sysLoginUserVo, DeviceType deviceType) {
        SaHolder.getStorage().set(LOGIN_KEY, sysLoginUserVo);
        StpUtil.login(sysLoginUserVo.getUsername(), deviceType.getCode());
        StpUtil.getTokenSession().set(LOGIN_KEY, sysLoginUserVo);
        saveLoginCache(sysLoginUserVo.getId(), sysLoginUserVo.getUsername());
    }

    /**
     * 记录登录缓存
     *
     * @param userId
     * @param username
     */
    public static void saveLoginCache(String userId, String username) {
        CacheUtil.setValue("Authorization:cache:" + userId, username, timeOut, TimeUnit.SECONDS);
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
    public static void logoutByUsername(String username) {
        StpUtil.kickout(username);
//        StpUtil.logout(username);
    }

    /**
     * 退出登录
     */
    public static void logoutByToken(String token) {
        StpUtil.kickoutByTokenValue(token);
//        StpUtil.logoutByTokenValue(token);
    }

    /**
     * 退出登录
     */
    public static void logoutByLoginId(String loginId) {
        StpUtil.kickout(loginId);
//        StpUtil.logout(loginId);
    }


    /**
     * 退出登录
     *
     * @param roleId
     */
    public static void logoutByRoleId(String roleId) {
        List<String> loginSessionList = getLoginSessionList();
        if (CollUtil.isEmpty(loginSessionList)) {
            return;
        }
        loginSessionList.parallelStream().forEach(sessionId -> {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            String tokenValue = session.getTerminalList().get(0).getTokenValue();
            String loginId = (String) session.getLoginId();
            // 如果已经过期则跳过
            long tokenTimeout = getTokenTimeoutByLoginId(loginId);
            if (tokenTimeout != -1 && tokenTimeout <= 0) {
                return;
            }
            SysLoginUserVo loginUserVo = (SysLoginUserVo) StpUtil.getTokenSessionByToken(tokenValue).get(LOGIN_KEY);
            if (loginUserVo.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
                try {
                    logoutByToken(tokenValue);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }

    /**
     * 退出登录
     *
     * @param roleIds
     */
    public static void logoutByRoleIds(List<String> roleIds) {
        List<String> loginSessionList = getLoginSessionList();
        if (CollUtil.isEmpty(loginSessionList)) {
            return;
        }
        loginSessionList.parallelStream().forEach(sessionId -> {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            String tokenValue = session.getTerminalList().get(0).getTokenValue();
            String loginId = (String) session.getLoginId();
            // 如果已经过期则跳过
            long tokenTimeout = getTokenTimeoutByLoginId(loginId);
            if (tokenTimeout != -1 && tokenTimeout <= 0) {
                return;
            }
            SysLoginUserVo loginUserVo = (SysLoginUserVo) StpUtil.getTokenSessionByToken(tokenValue).get(LOGIN_KEY);
            if (loginUserVo.getRoles().stream().anyMatch(r -> roleIds.contains(r.getId()))) {
                try {
                    logoutByLoginId(tokenValue);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }


    /**
     * 下线相关用户
     *
     * @param positionId
     */
    public static void logoutByPositionId(String positionId) {
        List<String> loginSessionList = getLoginSessionList();
        if (CollUtil.isEmpty(loginSessionList)) {
            return;
        }
        loginSessionList.parallelStream().forEach(sessionId -> {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            String tokenValue = session.getTerminalList().get(0).getTokenValue();
            String loginId = (String) session.getLoginId();
            // 如果已经过期则跳过
            long tokenTimeout = getTokenTimeoutByLoginId(loginId);
            if (tokenTimeout != -1 && tokenTimeout <= 0) {
                return;
            }
            SysLoginUserVo loginUserVo = (SysLoginUserVo) StpUtil.getTokenSessionByToken(tokenValue).get(LOGIN_KEY);
            if (loginUserVo.getPositions().stream().anyMatch(r -> r.getId().equals(positionId))) {
                try {
                    logoutByLoginId(loginId);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }

    /**
     * 下线相关用户
     *
     * @param positionIds
     */
    public static void logoutByPositionIds(List<String> positionIds) {
        List<String> loginSessionList = getLoginSessionList();
        if (CollUtil.isEmpty(loginSessionList)) {
            return;
        }
        loginSessionList.parallelStream().forEach(sessionId -> {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            String tokenValue = session.getTerminalList().get(0).getTokenValue();
            String loginId = (String) session.getLoginId();
            // 如果已经过期则跳过
            long tokenTimeout = getTokenTimeoutByLoginId(loginId);
            if (tokenTimeout != -1 && tokenTimeout <= 0) {
                return;
            }
            SysLoginUserVo loginUserVo = (SysLoginUserVo) StpUtil.getTokenSessionByToken(tokenValue).get(LOGIN_KEY);
            if (loginUserVo.getPositions().stream().anyMatch(r -> positionIds.contains(r.getId()))) {
                try {
                    logoutByLoginId(loginId);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }


    /**
     * 退出登录
     *
     * @param userId
     */
    public static void logoutByUserId(String userId) {
        String username = (String) CacheUtil.getValue("Authorization:cache:" + userId);
        if (StringUtils.isNotBlank(username)) {
            logoutByUsername(username);
            CacheUtil.del("Authorization:cache:" + userId);
        }
    }

    /**
     * 获取登录用户
     */
    public static SysLoginUserVo getLoginUser() {
        try {
            SysLoginUserVo loginUser = (SysLoginUserVo) SaHolder.getStorage().get(LOGIN_KEY);
            if (loginUser != null) {
                return loginUser;
            }
            SaSession tokenSession;
            try {
                tokenSession = StpUtil.getTokenSession();
            } catch (Exception e) {
                return null;
            }
            if (tokenSession == null) {
                return null;
            }
            loginUser = (SysLoginUserVo) tokenSession.get(LOGIN_KEY);
            SaHolder.getStorage().set(LOGIN_KEY, loginUser);
            return loginUser;
        } catch (Exception e) {
            return null;
        }

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
        SysLoginUserVo loginUser = getLoginUser();
        if (loginUser == null) {
            return null;
        }
        return loginUser.getId();
    }

    /**
     * 是否为管理员
     *
     * @return
     */

    public static boolean isAdmin() {
        SysLoginUserVo loginUser = getLoginUser();
        return loginUser.getIsAdmin();
    }

    /**
     * 获取当前token列表（包含已经退出登录的和已登录的）
     *
     * @return
     */
    public static List<String> getLoginTokenList() {
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        return keys;
    }

    /**
     * 获取登录用户的session列表（只包含已登录的）
     *
     * @return
     */
    public static List<String> getLoginSessionList() {
        List<String> keys = StpUtil.searchSessionId("", 0, -1, false);
        return keys;
    }

    /**
     * 获取token的有效期，（单位：s，返回-1代表永久有效）
     *
     * @param token
     * @return
     */
    public static long getTokenTimeoutByToken(String token) {
        return StpUtil.stpLogic.getTokenTimeout(token);
    }

    /**
     * 获取token的有效期，（单位：s，返回-1代表永久有效）
     *
     * @param loginId
     * @return
     */
    public static long getTokenTimeoutByLoginId(String loginId) {
        return StpUtil.stpLogic.getTokenTimeoutByLoginId(loginId);
    }
}
