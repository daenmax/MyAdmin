package cn.daenx.myadmin.system.service;


import java.util.List;

public interface LoginUtilService {
    /**
     * 下线相关用户
     *
     * @param roleId
     */
    void offLineUser(String roleId);
    /**
     * 下线相关用户
     *
     * @param roleIds
     */
    void offLineUser(List<String> roleIds);
}
