package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.RouterVo;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends IService<SysMenu> {
    Set<String> getMenuPermissionByUser(SysLoginUserVo loginUserVo);

    List<SysMenu> getMenuTreeByUserId(String userId);
    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

}
