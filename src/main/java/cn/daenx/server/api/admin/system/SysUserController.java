package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.vo.*;
import cn.daenx.system.service.SysDeptService;
import cn.daenx.system.service.SysUserService;
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
        List<SysDeptTree> trees = sysDeptService.deptTreeNew(new SysDeptPageVo());
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
    public Result page(SysUserPageVo vo) {
        IPage<SysUserPageDto> page = sysUserService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:user:export")
    @PostMapping("/exportData")
    public void exportData(SysUserPageVo vo, HttpServletResponse response) {
        List<SysUserPageDto> list = sysUserService.exportData(vo);
        ExcelUtil.exportXlsx(response, "用户", "系统用户", list, SysUserPageDto.class);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
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
    public Result edit(@Validated @RequestBody SysUserUpdVo vo) {
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
    public Result add(@Validated @RequestBody SysUserAddVo vo) {
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
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
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
    public Result resetPwd(@Validated @RequestBody SysUserResetPwdVo vo) {
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
    public Result saveAuthRole(@Validated @RequestBody SysUserUpdAuthRoleVo vo) {
        sysUserService.saveAuthRole(vo);
        return Result.ok();
    }


}
