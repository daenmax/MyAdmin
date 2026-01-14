package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.vo.RouterVo;
import cn.daenx.framework.common.domain.vo.system.other.SysLoginUserVo;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuAddDto;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuPageDto;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuUpdDto;
import cn.daenx.modules.system.domain.po.SysMenu;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 列表
     *
     * @param dto
     * @return
     */
    List<SysMenu> getList(SysMenuPageDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysMenu getInfo(String id);

    /**
     * 修改
     *
     * @param dto
     */
    void editInfo(SysMenuUpdDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysMenuAddDto dto);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 检查是否存在，已存在返回true
     *
     * @param name
     * @param nowId 排除ID
     * @return
     */
    Boolean checkMenuExist(String name, String nowId);

    Set<String> getMenuPermissionByUser(SysLoginUserVo loginUserVo);

    List<SysMenu> getMenuTreeByUserId(String userId, Boolean isAdmin);

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
     * @param dto
     * @return
     */
    List<Tree<String>> treeSelect(SysMenuPageDto dto);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectMenuListByRoleId(String roleId);
}
