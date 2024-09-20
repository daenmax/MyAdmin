package cn.daenx.system.service;

import cn.daenx.framework.common.vo.RouterVo;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.daenx.system.domain.po.SysMenu;
import cn.daenx.system.domain.vo.SysMenuAddVo;
import cn.daenx.system.domain.vo.SysMenuPageVo;
import cn.daenx.system.domain.vo.SysMenuUpdVo;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    List<SysMenu> getList(SysMenuPageVo vo);

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
     * @param vo
     */
    void editInfo(SysMenuUpdVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysMenuAddVo vo);

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
     * @param vo
     * @return
     */
    List<Tree<String>> treeSelect(SysMenuPageVo vo);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectMenuListByRoleId(String roleId);
}
