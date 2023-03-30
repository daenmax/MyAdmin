package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.service.SysDeptService;
import cn.daenx.myadmin.system.service.SysRoleService;
import cn.daenx.myadmin.system.service.SysUserService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;


    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:list")
    @GetMapping(value = "/list")
    public Result list(SysRolePageVo vo) {
        IPage<SysRole> page = sysRoleService.getPage(vo);
        return Result.ok(page);
    }

}
