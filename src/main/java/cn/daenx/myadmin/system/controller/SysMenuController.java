package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.service.SysMenuService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping(value = "/list")
    public Result list(SysMenuPageVo vo) {
        List<SysMenu> list = sysMenuService.getList(vo);
        return Result.ok(list);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysMenu sysMenu = sysMenuService.getInfo(id);
        return Result.ok(sysMenu);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:menu:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysMenuUpdVo vo) {
        sysMenuService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:menu:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysMenuAddVo vo) {
        sysMenuService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:menu:remove")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MyException("参数错误");
        }
        sysMenuService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取菜单下拉树列表
     *
     * @param vo
     * @return
     */
    @GetMapping(value = "/treeSelect")
    public Result treeSelect(SysMenuPageVo vo) {
        List<Tree<String>> list = sysMenuService.treeSelect(vo);
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
        List<Tree<String>> list = sysMenuService.treeSelect(new SysMenuPageVo());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", list);
        return Result.ok(map);
    }

}
