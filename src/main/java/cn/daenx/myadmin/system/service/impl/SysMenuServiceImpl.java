package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.StreamUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.MetaVo;
import cn.daenx.myadmin.system.vo.RouterVo;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.mapper.SysMenuMapper;
import cn.daenx.myadmin.system.service.SysMenuService;

import java.util.*;

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

    @Override
    public List<SysMenu> getMenuTreeByUserId(String userId) {
        List<SysMenu> menus = null;
        if (LoginUtil.isAdmin(userId)) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                    .in(SysMenu::getMenuType, SystemConstant.MENU_TYPE_DIR, SystemConstant.MENU_TYPE_MENU)
                    .eq(SysMenu::getStatus, "0")
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
        return StreamUtils.filter(list, n -> n.getParentId().equals(t.getId()));
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
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && SystemConstant.MENU_TYPE_DIR.equals(menu.getMenuType())) {
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
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (("0".equals(menu.getParentId())) && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(SystemConstant.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }
}
