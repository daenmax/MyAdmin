package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.service.SysMenuService;
import cn.daenx.myadmin.system.service.SysRoleService;
import cn.daenx.myadmin.system.vo.SysMenuPageVo;
import cn.daenx.myadmin.system.vo.SysRolePageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;


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

}
