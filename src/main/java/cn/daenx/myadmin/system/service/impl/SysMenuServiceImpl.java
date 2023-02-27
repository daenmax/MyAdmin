package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.mapper.SysMenuMapper;
import cn.daenx.myadmin.system.service.SysMenuService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String> getMenuPermissionByUser(SysLoginUserVo loginUserVo) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (loginUserVo.isAdmin()) {
            perms.add("*:*:*");
        } else {
            if (!loginUserVo.getRoles().isEmpty() && loginUserVo.getRoles().size() > 1) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : loginUserVo.getRoles()) {
                    Set<String> rolePerms = getMenuPerms(role.getId(), 1);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(getMenuPerms(loginUserVo.getId(), 2));
            }
        }
        return perms;
    }

    /**
     * 获取菜单权限列表
     *
     * @param str
     * @param type 1=根据roleId，2=根据userId
     * @return
     */
    private Set<String> getMenuPerms(String str, int type) {
        List<String> perms = null;
        if (type == 1) {
            perms = sysMenuMapper.getMenuPermsByRoleId(str);
        } else {
            perms = sysMenuMapper.getMenuPermsByUserId(str);
        }
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (ObjectUtil.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
}
