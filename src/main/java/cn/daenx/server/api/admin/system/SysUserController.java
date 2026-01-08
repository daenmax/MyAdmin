package cn.daenx.server.api.admin.system;

import cn.daenx.modules.system.domain.vo.sysDept.SysDeptTreeVo;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptPageDto;
import cn.daenx.modules.system.domain.dto.sysUser.*;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
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
    public Result deptTree() {
//        List<Tree<String>> trees = sysDeptService.deptTree(new SysDeptPageVo());
        List<SysDeptTreeVo> trees = sysDeptService.deptTreeNew(new SysDeptPageDto());
        return Result.ok(trees);
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:page")
    @GetMapping(value = "/page")
    public Result page(SysUserPageDto vo) {
        IPage<SysUserPageVo> page = sysUserService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:user:export")
    @PostMapping("/exportData")
    public void exportData(SysUserPageDto vo, HttpServletResponse response) {
        List<SysUserPageVo> list = sysUserService.exportData(vo);
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
    public Result query(@RequestParam(name = "id", required = false) String id) {
        Map<String, Object> map = sysUserService.getInfo(id);
        return Result.ok(map);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysUserUpdDto vo) {
        sysUserService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysUserAddDto vo) {
        sysUserService.addInfo(vo);
        return Result.ok();
    }


    /**
     * 修改状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:edit")
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdDto vo) {
        sysUserService.changeStatus(vo);
        return Result.ok();
    }


    /**
     * 重置用户密码
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:resetPwd")
    @PostMapping("/resetPwd")
    public Result resetPwd(@Validated @RequestBody SysUserResetPwdDto vo) {
        sysUserService.resetPwd(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:user:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
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
    public Result authRole(@PathVariable(value = "id", required = false) String id) {
        Map<String, Object> map = sysUserService.authRole(id);
        return Result.ok(map);
    }

    /**
     * 保存用户授权角色
     *
     * @param vo
     */
    @SaCheckPermission("system:user:edit")
    @PostMapping("/authRole")
    public Result saveAuthRole(@Validated @RequestBody SysUserUpdAuthRoleDto vo) {
        sysUserService.saveAuthRole(vo);
        return Result.ok();
    }


}
