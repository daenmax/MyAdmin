package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserService extends IService<SysUser> {

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    SysUser getUserByUserId(String userId);

    /**
     * 校验用户状态是否正常
     *
     * @param sysUser
     * @return
     */
    Boolean validatedUser(SysUser sysUser);

    /**
     * 注册用户
     *
     * @param sysUser
     * @param roleId
     * @return
     */
    Boolean registerUser(SysUser sysUser, String roleId);

}
