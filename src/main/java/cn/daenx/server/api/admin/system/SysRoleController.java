package cn.daenx.server.api.admin.system;

import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptPageDto;
import cn.daenx.modules.system.domain.dto.sysRole.*;
import cn.daenx.modules.system.domain.dto.sysUser.SysUserPageDto;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.po.SysRole;
import cn.daenx.modules.system.service.SysDeptService;
import cn.daenx.modules.system.service.SysRoleService;
import cn.daenx.modules.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysUserService sysUserService;


    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:page")
    @GetMapping(value = "/page")
    public Result page(SysRolePageDto vo) {
        IPage<SysRole> page = sysRoleService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:role:export")
    @PostMapping("/exportData")
    public void exportData(SysRolePageDto vo, HttpServletResponse response) {
        List<SysRole> list = sysRoleService.getAll(vo);
        ExcelUtil.exportXlsx(response, "角色", "角色数据", list, SysRole.class);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        SysRole sysRole = sysRoleService.getInfo(id);
        return Result.ok(sysRole);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysRoleUpdDto vo) {
        sysRoleService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysRoleAddDto vo) {
        sysRoleService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:role:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysRoleService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 获取对应角色部门列表树
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/roleDeptTreeSelect/{roleId}")
    public Result roleDeptTreeSelect(@PathVariable("roleId") String roleId) {
        List<Tree<String>> list = sysDeptService.deptTree(new SysDeptPageDto());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysDeptService.selectDeptListByRoleId(roleId));
        map.put("depts", list);
        return Result.ok(map);
    }


    /**
     * 修改数据权限
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/dataScope")
    public Result dataScope(@Validated @RequestBody SysRoleDataScopeUpdDto vo) {
        sysRoleService.dataScope(vo);
        return Result.ok();
    }


    /**
     * 查询已分配该角色的用户列表
     */
    @SaCheckPermission("system:role:page")
    @GetMapping("/allocatedAuthUserPage")
    public Result allocatedPage(SysUserPageDto vo, String roleId) {
        if (ObjectUtil.isEmpty(roleId)) {
            throw new MyException("roleId不能为空");
        }
        IPage<SysUserPageVo> page = sysUserService.getUserListByRoleId(vo, roleId);
        return Result.ok(page);
    }

    /**
     * 查询未分配该角色的用户列表
     */
    @SaCheckPermission("system:role:page")
    @GetMapping("/unallocatedAuthUserPage")
    public Result unallocatedAuthUserPage(SysUserPageDto vo, String roleId) {
        if (ObjectUtil.isEmpty(roleId)) {
            throw new MyException("roleId不能为空");
        }
        IPage<SysUserPageVo> page = sysUserService.getUserListByUnRoleId(vo, roleId);
        return Result.ok(page);
    }

    /**
     * 取消授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/cancelAuthUser")
    public Result cancelAuthUser(@Validated @RequestBody SysRoleUpdAuthUserDto vo) {
        sysRoleService.cancelAuthUser(vo);
        return Result.ok();
    }

    /**
     * 保存授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/saveAuthUser")
    public Result saveAuthUser(@Validated @RequestBody SysRoleUpdAuthUserDto vo) {
        sysRoleService.saveAuthUser(vo);
        return Result.ok();
    }


    /**
     * 修改状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdDto vo) {
        sysRoleService.changeStatus(vo);
        return Result.ok();
    }
}
