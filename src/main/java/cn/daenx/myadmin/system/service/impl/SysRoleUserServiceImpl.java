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
     * 删除用户的所有角色关联信息
     *
     * @param userIds
     */
    @Override
    public void delUserRole(List<String> userIds) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysRoleUser::getUserId, userIds);
        sysRoleUserMapper.delete(wrapper);
    }

    /**
     * 删除用户的指定角色
     * 如果是最后一个角色，那么将不会删除
     *
     * @param userId
     * @param roleId
     */
    @Override
    public Boolean delUserRole(String userId, String roleId) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, userId);
        Long aLong = sysRoleUserMapper.selectCount(wrapper);
        if (aLong <= 1) {
            return false;
        }
        LambdaQueryWrapper<SysRoleUser> wrapperDel = new LambdaQueryWrapper<>();
        wrapperDel.eq(SysRoleUser::getUserId, userId);
        wrapperDel.eq(SysRoleUser::getRoleId, roleId);
        return sysRoleUserMapper.delete(wrapperDel) > 0;
    }
}
