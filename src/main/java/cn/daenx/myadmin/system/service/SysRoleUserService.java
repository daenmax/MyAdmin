package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.domain.po.SysRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleUserService extends IService<SysRoleUser> {


    /**
     * 更新用户角色关联信息
     *
     * @param userId
     * @param roleIds
     */
    void handleUserRole(String userId, List<String> roleIds);

    /**
     * 删除用户的所有角色关联信息
     *
     * @param userIds
     */
    void delUserRole(List<String> userIds);

    /**
     * 删除用户的指定角色
     * 如果是最后一个角色，那么将不会删除
     *
     * @param userId
     * @param roleId
     */
    Boolean delUserRole(String userId, String roleId);

    /**
     * 给用户添加指定角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    Boolean addUserRole(String userId, String roleId);

}
