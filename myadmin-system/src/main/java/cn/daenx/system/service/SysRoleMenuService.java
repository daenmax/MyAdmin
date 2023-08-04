package cn.daenx.system.service;

import cn.daenx.system.domain.po.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu>{
    /**
     * 更新角色菜单关联信息
     *
     * @param roleId
     * @param menuIds
     */
    void handleRoleMenu(String roleId, List<String> menuIds);

}
