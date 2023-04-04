package cn.daenx.myadmin.system.service;


import cn.daenx.myadmin.system.vo.SysLoginUserVo;

import java.util.List;

public interface LoginUtilService {
    /**
     * 下线相关用户
     *
     * @param roleId
     */
    void offLineUserByRoleId(String roleId);

    /**
     * 下线相关用户
     *
     * @param roleIds
     */
    void offLineUserByRoleId(List<String> roleIds);

    /**
     * 下线相关用户
     *
     * @param userId
     */
    void offLineUserByUserId(String userId);

    /**
     * 记录登录缓存
     *
     * @param userId
     * @param username
     */
    void saveLoginCache(String userId, String username);
}
