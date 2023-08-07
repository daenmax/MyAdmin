package cn.daenx.web.controller.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.SysRole;
import cn.daenx.system.domain.vo.*;
import cn.daenx.system.service.SysDeptService;
import cn.daenx.system.service.SysRoleService;
import cn.daenx.system.service.SysUserService;
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
    @SaCheckPermission("system:role:list")
    @GetMapping(value = "/list")
    public Result list(SysRolePageVo vo) {
        IPage<SysRole> page = sysRoleService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:role:export")
    @PostMapping("/export")
    public void export(SysRolePageVo vo, HttpServletResponse response) {
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
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
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
    public Result edit(@Validated @RequestBody SysRoleUpdVo vo) {
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
    public Result add(@Validated @RequestBody SysRoleAddVo vo) {
        sysRoleService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:role:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
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
        List<Tree<String>> list = sysDeptService.deptTree(new SysDeptPageVo());
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
    public Result dataScope(@Validated @RequestBody SysRoleDataScopeUpdVo vo) {
        sysRoleService.dataScope(vo);
        return Result.ok();
    }


    /**
     * 查询已分配该角色的用户列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public Result allocatedList(SysUserPageVo vo, String roleId) {
        if (ObjectUtil.isEmpty(roleId)) {
            throw new MyException("roleId不能为空");
        }
        IPage<SysUserPageDto> page = sysUserService.getUserListByRoleId(vo, roleId);
        return Result.ok(page);
    }

    /**
     * 查询未分配该角色的用户列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public Result unallocatedList(SysUserPageVo vo, String roleId) {
        if (ObjectUtil.isEmpty(roleId)) {
            throw new MyException("roleId不能为空");
        }
        IPage<SysUserPageDto> page = sysUserService.getUserListByUnRoleId(vo, roleId);
        return Result.ok(page);
    }

    /**
     * 取消授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/authUser/cancel")
    public Result cancelAuthUser(@Validated @RequestBody SysRoleUpdAuthUserVo vo) {
        sysRoleService.cancelAuthUser(vo);
        return Result.ok();
    }

    /**
     * 保存授权用户
     *
     * @param vo
     */
    @SaCheckPermission("system:role:edit")
    @PostMapping("/authUser/save")
    public Result saveAuthUser(@Validated @RequestBody SysRoleUpdAuthUserVo vo) {
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
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        sysRoleService.changeStatus(vo);
        return Result.ok();
    }
}
