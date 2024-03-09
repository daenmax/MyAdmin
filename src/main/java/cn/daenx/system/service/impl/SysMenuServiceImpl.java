package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.TreeBuildUtils;
import cn.daenx.framework.common.vo.RouterVo;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.common.vo.system.other.SysRoleVo;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.system.domain.po.SysMenu;
import cn.daenx.system.domain.po.SysRole;
import cn.daenx.system.domain.po.SysRoleMenu;
import cn.daenx.system.domain.vo.SysMenuAddVo;
import cn.daenx.system.domain.vo.SysMenuPageVo;
import cn.daenx.system.domain.vo.SysMenuUpdVo;
import cn.daenx.system.mapper.SysRoleMapper;
import cn.daenx.system.mapper.SysRoleMenuMapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.system.mapper.SysMenuMapper;
import cn.daenx.system.service.SysMenuService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;


    /**
     * 列表
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysMenu> getList(SysMenuPageVo vo) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        List<SysMenu> menus = null;
        if (loginUser.getIsAdmin()) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(ObjectUtil.isNotEmpty(vo.getMenuName()), SysMenu::getMenuName, vo.getMenuName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(vo.getVisible()), SysMenu::getVisible, vo.getVisible());
            queryWrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysMenu::getStatus, vo.getStatus());
            queryWrapper.orderByAsc(SysMenu::getParentId);
            queryWrapper.orderByAsc(SysMenu::getOrderNum);
            menus = sysMenuMapper.selectList(queryWrapper);
        } else {
            QueryWrapper<SysMenu> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("sru.user_id", loginUser.getId());
            wrapper1.like(ObjectUtil.isNotEmpty(vo.getMenuName()), "sm.menu_name", vo.getMenuName());
            wrapper1.eq(ObjectUtil.isNotEmpty(vo.getVisible()), "sm.visible", vo.getVisible());
            wrapper1.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "sm.status", vo.getStatus());
            wrapper1.orderByAsc("sm.parent_id");
            wrapper1.orderByAsc("sm.order_num");
            menus = sysMenuMapper.getMenuList(wrapper1);
        }
        return menus;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysMenu getInfo(String id) {
        return sysMenuMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param vo
     */
    @Override
    public void editInfo(SysMenuUpdVo vo) {
        if (checkMenuExist(vo.getMenuName(), vo.getId())) {
            throw new MyException("字典名称已存在");
        }
        if ("0".equals(vo.getIsFrame()) && !Validator.isUrl(vo.getPath())) {
            throw new MyException("地址必须以http(s)://开头");
        }
        if (vo.getId().equals(vo.getParentId())) {
            throw new MyException("上级菜单不能选择自己");
        }
        if (ObjectUtil.isEmpty(vo.getIcon())) {
            vo.setIcon("#");
        }
        LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysMenu::getId, vo.getId());
        wrapper.set(SysMenu::getParentId, vo.getParentId());
        wrapper.set(SysMenu::getMenuName, vo.getMenuName());
        wrapper.set(SysMenu::getOrderNum, vo.getOrderNum());
        wrapper.set(SysMenu::getPath, vo.getPath());
        wrapper.set(SysMenu::getQueryParam, vo.getQueryParam());
        wrapper.set(SysMenu::getComponent, vo.getComponent());
        wrapper.set(SysMenu::getPerms, vo.getPerms());
        wrapper.set(SysMenu::getIcon, vo.getIcon());
        wrapper.set(SysMenu::getVisible, vo.getVisible());
        wrapper.set(SysMenu::getStatus, vo.getStatus());
        wrapper.set(SysMenu::getMenuType, vo.getMenuType());
        wrapper.set(SysMenu::getIsFrame, vo.getIsFrame());
        wrapper.set(SysMenu::getIsCache, vo.getIsCache());
        wrapper.set(SysMenu::getRemark, vo.getRemark());
        int update = sysMenuMapper.update(new SysMenu(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 检查是否有子菜单
     *
     * @param id
     * @return
     */
    private Boolean checkHasChildren(String id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id);
        boolean exists = sysMenuMapper.exists(queryWrapper);
        return exists;
    }

    /**
     * 检查是否被角色分配使用
     *
     * @param id
     * @return
     */
    private Boolean checkHasMakeRole(String id) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getMenuId, id);
        boolean exists = sysRoleMenuMapper.exists(queryWrapper);
        return exists;
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysMenuAddVo vo) {
        if (checkMenuExist(vo.getMenuName(), null)) {
            throw new MyException("字典名称已存在");
        }
        if ("0".equals(vo.getIsFrame()) && !Validator.isUrl(vo.getPath())) {
            throw new MyException("地址必须以http(s)://开头");
        }
        if (ObjectUtil.isEmpty(vo.getIcon())) {
            vo.setIcon("#");
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(vo.getParentId());
        sysMenu.setMenuName(vo.getMenuName());
        sysMenu.setOrderNum(vo.getOrderNum());
        sysMenu.setPath(vo.getPath());
        sysMenu.setQueryParam(vo.getQueryParam());
        sysMenu.setComponent(vo.getComponent());
        sysMenu.setPerms(vo.getPerms());
        sysMenu.setIcon(vo.getIcon());
        sysMenu.setVisible(vo.getVisible());
        sysMenu.setStatus(vo.getStatus());
        sysMenu.setMenuType(vo.getMenuType());
        sysMenu.setIsFrame(vo.getIsFrame());
        sysMenu.setIsCache(vo.getIsCache());
        sysMenu.setRemark(vo.getRemark());
        int insert = sysMenuMapper.insert(sysMenu);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        if (checkHasChildren(id)) {
            throw new MyException("存在子菜单，请先删除子菜单");
        }
        if (checkHasMakeRole(id)) {
            throw new MyException("已被角色分配，请先删除分配");
        }
        int i = sysMenuMapper.deleteById(id);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param name
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkMenuExist(String name, String nowId) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getMenuName, name);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysMenu::getId, nowId);
        boolean exists = sysMenuMapper.exists(wrapper);
        return exists;
    }

    @Override
    public Set<String> getMenuPermissionByUser(SysLoginUserVo loginUserVo) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (loginUserVo.getIsAdmin()) {
            perms.add("*:*:*");
        } else {
            if (!loginUserVo.getRoles().isEmpty() && loginUserVo.getRoles().size() > 1) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRoleVo role : loginUserVo.getRoles()) {
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

    @Override
    public List<SysMenu> getMenuTreeByUserId(String userId, Boolean isAdmin) {
        List<SysMenu> menus = null;
        if (isAdmin) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                    .in(SysMenu::getMenuType, SystemConstant.MENU_TYPE_DIR, SystemConstant.MENU_TYPE_MENU)
                    .eq(SysMenu::getStatus, CommonConstant.STATUS_NORMAL)
                    .orderByAsc(SysMenu::getParentId)
                    .orderByAsc(SysMenu::getOrderNum);
            menus = sysMenuMapper.selectList(wrapper);
        } else {
            menus = sysMenuMapper.getMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, "0");
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, String parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (Objects.equals(t.getParentId(), parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 内链域名特殊字符替换
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{"http://", "https://", "www.", "."},
                new String[]{"", "", "", "/"});
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if ((!"0".equals(menu.getParentId())) && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if ("0".equals(menu.getParentId()) && SystemConstant.MENU_TYPE_DIR.equals(menu.getMenuType())
                && SystemConstant.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = SystemConstant.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && (!"0".equals(menu.getParentId())) && isInnerLink(menu)) {
            component = SystemConstant.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = SystemConstant.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu) {
        return ("0".equals(menu.getParentId())) && SystemConstant.MENU_TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(SystemConstant.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame().equals(SystemConstant.NO_FRAME) && Validator.isUrl(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return (!"0".equals(menu.getParentId())) && SystemConstant.MENU_TYPE_DIR.equals(menu.getMenuType());
    }


    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return list.stream().filter(n -> n.getParentId().equals(t.getId())).collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQueryParam());
            router.setMeta(new RouterVo.MetaVo(menu.getMenuName(), menu.getIcon(), "1".equals(menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (cMenus != null && !cMenus.isEmpty() && SystemConstant.MENU_TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new RouterVo.MetaVo(menu.getMenuName(), menu.getIcon(), "1".equals(menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (("0".equals(menu.getParentId())) && isInnerLink(menu)) {
                router.setMeta(new RouterVo.MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(SystemConstant.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new RouterVo.MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取菜单下拉树列表
     *
     * @param vo
     * @return
     */
    @Override
    public List<Tree<String>> treeSelect(SysMenuPageVo vo) {
        List<SysMenu> menus = getList(vo);
        return buildMenuTreeSelect(menus);
    }

    @Override
    public List<String> selectMenuListByRoleId(String roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        List<SysMenu> menuListByRoleId = sysMenuMapper.getMenuListByRoleId(sysRole.getId(), sysRole.getMenuCheckStrictly());
        List<String> strings = MyUtil.joinToList(menuListByRoleId, SysMenu::getId);
        return strings;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param all 菜单列表
     * @return 下拉树结构列表
     */
    public List<Tree<String>> buildMenuTreeSelect(List<SysMenu> all) {
        if (CollUtil.isEmpty(all)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(all, (menu, tree) ->
                tree.setId(menu.getId())
                        .setParentId(menu.getParentId())
                        .setName(menu.getMenuName())
                        .setWeight(menu.getOrderNum()));
    }
}
