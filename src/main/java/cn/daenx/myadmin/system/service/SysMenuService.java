package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.RouterVo;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.daenx.myadmin.system.vo.SysMenuPageVo;
import cn.hutool.core.lang.tree.Tree;
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

    /**
     * 获取菜单下拉树列表
     *
     * @param vo
     * @param userId
     * @param isAdmin
     * @return
     */
    List<Tree<String>> treeSelect(SysMenuPageVo vo, String userId, Boolean isAdmin);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectMenuListByRoleId(String roleId);

}
