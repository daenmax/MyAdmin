package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.system.po.SysRoleMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysRoleUser;
import cn.daenx.myadmin.system.mapper.SysRoleUserMapper;
import cn.daenx.myadmin.system.service.SysRoleUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;


    /**
     * 更新用户角色关联信息
     *
     * @param userId
     * @param roleIds
     */
    @Override
    public void handleUserRole(String userId, List<String> roleIds) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, userId);
        sysRoleUserMapper.delete(wrapper);
        if (roleIds != null) {
            List<SysRoleUser> list = new ArrayList<>();
            for (String roleId : roleIds) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(userId);
                list.add(sysRoleUser);
            }
            saveBatch(list);
        }
    }

    /**
     * 删除用户角色关联信息
     *
     * @param userIds
     */
    @Override
    public void delUserRole(List<String> userIds) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysRoleUser::getUserId, userIds);
        sysRoleUserMapper.delete(wrapper);
    }
}
