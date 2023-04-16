package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginUtilServiceImpl implements LoginUtilService {
    @Value("${sa-token.timeout}")
    private Long timeOut;
    public static String LOGIN_KEY = "system";


    /**
     * 登录
     *
     * @param sysLoginUserVo
     * @param deviceType
     */
    @Override
    public void login(SysLoginUserVo sysLoginUserVo, DeviceType deviceType) {
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
    private void saveLoginCache(String userId, String username) {
        RedisUtil.setValue("Authorization:cache:" + userId, username, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 退出登录
     */
    @Override
    public void logoutByUsername(String username) {
        StpUtil.kickout(username);
//        StpUtil.logout(username);
    }

    /**
     * 退出登录
     */
    @Override
    public void logoutByToken(String token) {
        StpUtil.kickoutByTokenValue(token);
//        StpUtil.logoutByTokenValue(token);
    }


    /**
     * 退出登录
     *
     * @param roleId
     */
    @Override
    @Async
    public void logoutByRoleId(String roleId) {
        List<String> loginTokenList = getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = getLoginUserByToken(token);
            if (loginUserByToken.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
                try {
                    logoutByToken(token);
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
    @Override
    @Async
    public void logoutByRoleIds(List<String> roleIds) {
        List<String> loginTokenList = getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = getLoginUserByToken(token);
            if (loginUserByToken.getRoles().stream().anyMatch(r -> roleIds.contains(r.getId()))) {
                try {
                    logoutByToken(token);
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
    @Override
    @Async
    public void logoutByPositionIds(List<String> positionIds) {
        List<String> loginTokenList = getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = getLoginUserByToken(token);
            if (loginUserByToken.getPositions().stream().anyMatch(r -> positionIds.contains(r.getId()))) {
                try {
                    logoutByToken(token);
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
    @Override
    public void logoutByPositionId(String positionId) {
        List<String> loginTokenList = getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = getLoginUserByToken(token);
            if (loginUserByToken.getPositions().stream().anyMatch(r -> r.getId().equals(positionId))) {
                try {
                    logoutByToken(token);
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
    @Override
    public void logoutByUserId(String userId) {
        String username = (String) RedisUtil.getValue("Authorization:cache:" + userId);
        if (StringUtils.isNotBlank(username)) {
            logoutByUsername(username);
            RedisUtil.del("Authorization:cache:" + userId);
        }
    }

    /**
     * 获取登录用户
     */
    @Override
    public SysLoginUserVo getLoginUser() {
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
    @Override
    public SysLoginUserVo getLoginUserByToken(String token) {
        return (SysLoginUserVo) StpUtil.getTokenSessionByToken(token).get(LOGIN_KEY);
    }

    /**
     * 获取登录用户ID
     */
    @Override
    public String getLoginUserId() {
        return getLoginUser().getId();
    }

    /**
     * 是否为管理员
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isAdmin(String userId) {
        return SystemConstant.IS_ADMIN_ID.equals(userId);
    }

    /**
     * 是否为管理员
     *
     * @return
     */
    @Override
    public boolean isAdmin() {
        return SystemConstant.IS_ADMIN_ID.equals(getLoginUserId());
    }

    /**
     * 获取登录用户的token列表
     *
     * @return
     */
    @Override
    public List<String> getLoginTokenList() {
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        return keys;
    }

    /**
     * 获取token的有效期
     *
     * @param token
     * @return
     */
    @Override
    public long getTokenActivityTimeoutByToken(String token) {
        return StpUtil.stpLogic.getTokenActivityTimeoutByToken(token);
    }
}
