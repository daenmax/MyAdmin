package cn.daenx.myadmin.system.service;


import cn.daenx.myadmin.common.enums.DeviceType;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;

import java.util.List;

public interface LoginUtilService {

    /**
     * 登录
     *
     * @param sysLoginUserVo
     * @param deviceType
     */
    void login(SysLoginUserVo sysLoginUserVo, DeviceType deviceType);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 退出登录
     */
    void logoutByUsername(String username);

    /**
     * 退出登录
     */
    void logoutByToken(String token);

    /**
     * 下线相关用户
     *
     * @param roleId
     */
    void logoutByRoleId(String roleId);

    /**
     * 下线相关用户
     *
     * @param roleIds
     */
    void logoutByRoleIds(List<String> roleIds);

    /**
     * 下线相关用户
     *
     * @param positionIds
     */
    void logoutByPositionIds(List<String> positionIds);

    /**
     * 下线相关用户
     *
     * @param positionId
     */
    void logoutByPositionId(String positionId);

    /**
     * 下线相关用户
     *
     * @param userId
     */
    void logoutByUserId(String userId);

    /**
     * 获取登录用户
     */
    SysLoginUserVo getLoginUser();

    /**
     * 获取登录用户
     */
    SysLoginUserVo getLoginUserByToken(String token);

    /**
     * 获取登录用户ID
     */
    String getLoginUserId();

    /**
     * 是否为管理员
     *
     * @param userId
     * @return
     */
    boolean isAdmin(String userId);

    /**
     * 是否为管理员
     *
     * @return
     */
    boolean isAdmin();

    /**
     * 获取登录用户的token列表获取登录用户的token列表
     *
     * @return
     */
    List<String> getLoginTokenList();

    /**
     * 获取token的有效期
     *
     * @param token
     * @return
     */
    long getTokenActivityTimeoutByToken(String token);
}
