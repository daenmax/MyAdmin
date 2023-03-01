package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysRoleUser;
import cn.daenx.myadmin.system.mapper.SysRoleUserMapper;
import cn.daenx.myadmin.system.service.SysRoleUserService;

import java.util.List;

@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 创建用户、角色关联
     *
     * @param roleId
     * @param userId
     * @return
     */
    @Override
    public Boolean createRoleUser(String roleId, String userId) {
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(roleId);
        sysRoleUser.setUserId(userId);
        return sysRoleUserMapper.insert(sysRoleUser) > 0;
    }
}
