package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleUserService extends IService<SysRoleUser> {

    /**
     * 创建用户、角色关联
     *
     * @param roleId
     * @param userId
     * @return
     */
    Boolean createRoleUser(String roleId, String userId);

}
