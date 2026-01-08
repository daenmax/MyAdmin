package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.modules.system.domain.po.SysMenu;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuAddDto;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuPageDto;
import cn.daenx.modules.system.domain.dto.sysMenu.SysMenuUpdDto;
import cn.daenx.modules.system.service.SysMenuService;
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
    @SaCheckPermission("system:menu:page")
    @GetMapping(value = "/page")
    public Result page(SysMenuPageDto vo) {
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
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
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
    public Result edit(@Validated @RequestBody SysMenuUpdDto vo) {
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
    public Result add(@Validated @RequestBody SysMenuAddDto vo) {
        sysMenuService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:menu:del")
    @PostMapping("/del")
    public Result del(@RequestParam(value = "id") String id) {
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
    public Result treeSelect(SysMenuPageDto vo) {
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
        List<Tree<String>> list = sysMenuService.treeSelect(new SysMenuPageDto());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", list);
        return Result.ok(map);
    }

}
