package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRoleUser;
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
     * 删除用户角色关联信息
     *
     * @param userIds
     */
    void delUserRole(List<String> userIds);
}
