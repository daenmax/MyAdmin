package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends IService<SysRole>{

    List<SysRole> getSysRoleListByUserId(String userId);
    Set<String> getRolePermissionListByUserId(String userId);
}
