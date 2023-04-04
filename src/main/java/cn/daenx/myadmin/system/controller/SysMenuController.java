package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.service.SysMenuService;
import cn.daenx.myadmin.system.vo.SysMenuPageVo;
import cn.hutool.core.lang.tree.Tree;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private LoginUtilService loginUtilService;


    /**
     * 获取菜单下拉树列表
     *
     * @param vo
     * @return
     */
    @GetMapping(value = "/treeSelect")
    public Result treeSelect(SysMenuPageVo vo) {
        List<Tree<String>> list = sysMenuService.treeSelect(vo, loginUtilService.getLoginUserId(), loginUtilService.isAdmin());
        return Result.ok(list);
    }


    /**
     * 获取对应角色菜单列表树
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public Result roleMenuTreeSelect(@PathVariable("roleId") String roleId) {
        List<Tree<String>> list = sysMenuService.treeSelect(new SysMenuPageVo(), loginUtilService.getLoginUserId(), loginUtilService.isAdmin());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", list);
        return Result.ok(map);
    }

}
