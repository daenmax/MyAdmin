package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptPageDto;
import cn.daenx.modules.system.domain.dto.sysUser.*;
import cn.daenx.modules.system.domain.vo.sysDept.SysDeptTreeVo;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.service.SysDeptService;
import cn.daenx.modules.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysDeptService sysDeptService;



    /**
     * 获取部门树列表
     *
     * @return
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    public Result<List<SysDeptTreeVo>> deptTree() {
//        List<Tree<String>> trees = sysDeptService.deptTree(new SysDeptPageVo());
        List<SysDeptTreeVo> trees = sysDeptService.deptTreeNew(new SysDeptPageDto());
        return Result.ok(trees);
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:user:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysUserPageVo>> page(SysUserPageDto dto) {
        IPage<SysUserPageVo> page = sysUserService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:user:export")
    @PostMapping("/exportData")
    public void exportData(SysUserPageDto dto, HttpServletResponse response) {
        List<SysUserPageVo> list = sysUserService.exportData(dto);
        ExcelUtil.exportXlsx(response, "用户", "系统用户", list, SysUserPageVo.class);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = "/query")
    public Result<Map<String, Object>> query(@RequestParam(name = "id", required = false) String id) {
        Map<String, Object> map = sysUserService.getInfo(id);
        return Result.ok(map);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:user:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysUserUpdDto dto) {
        sysUserService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:user:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysUserAddDto dto) {
        sysUserService.addInfo(dto);
        return Result.ok();
    }


    /**
     * 修改状态
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:user:edit")
    @PostMapping("/changeStatus")
    public Result<Void> changeStatus(@Validated @RequestBody ComStatusUpdDto dto) {
        sysUserService.changeStatus(dto);
        return Result.ok();
    }


    /**
     * 重置用户密码
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:user:resetPwd")
    @PostMapping("/resetPwd")
    public Result<Void> resetPwd(@Validated @RequestBody SysUserResetPwdDto dto) {
        sysUserService.resetPwd(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:user:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysUserService.deleteByIds(ids);
        return Result.ok();
    }


    /**
     * 根据用户编号获取授权角色
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = {"/authRole/{id}"})
    public Result<Map<String, Object>> authRole(@PathVariable(value = "id", required = false) String id) {
        Map<String, Object> map = sysUserService.authRole(id);
        return Result.ok(map);
    }

    /**
     * 保存用户授权角色
     *
     * @param dto
     */
    @SaCheckPermission("system:user:edit")
    @PostMapping("/authRole")
    public Result<Void> saveAuthRole(@Validated @RequestBody SysUserUpdAuthRoleDto dto) {
        sysUserService.saveAuthRole(dto);
        return Result.ok();
    }


}
