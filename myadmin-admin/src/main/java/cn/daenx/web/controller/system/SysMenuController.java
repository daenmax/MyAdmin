package cn.daenx.web.controller.system;

import cn.daenx.common.exception.MyException;
import cn.daenx.common.vo.Result;
import cn.daenx.system.domain.po.SysMenu;
import cn.daenx.system.domain.vo.SysMenuAddVo;
import cn.daenx.system.domain.vo.SysMenuPageVo;
import cn.daenx.system.domain.vo.SysMenuUpdVo;
import cn.daenx.system.service.SysMenuService;
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
    @PostMapping("/edit")
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
    @PostMapping("/add")
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
    @PostMapping("/remove/{id}")
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
