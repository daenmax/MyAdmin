package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.service.SysDeptService;
import cn.daenx.myadmin.system.service.SysUserService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
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
     * 个人信息
     *
     * @return
     */
    @GetMapping("/profile")
    public Result profile() {
        Map<String, Object> map = sysUserService.profile();
        return Result.ok(map);
    }

    /**
     * 修改个人资料
     *
     * @return
     */
    @PutMapping("/profile")
    public Result edit(@Validated @RequestBody SysUserUpdInfoVo vo) {
        sysUserService.updInfo(vo);
        return Result.ok();
    }

    /**
     * 修改个人密码
     *
     * @return
     */
    @PutMapping("/updatePwd")
    public Result updatePwd(@Validated @RequestBody SysUserUpdPwdVo vo) {
        sysUserService.updatePwd(vo);
        return Result.ok("修改成功，请重新登录", null);
    }

    /**
     * 获取部门树列表
     *
     * @return
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    public Result deptTree() {
        List<Tree<String>> trees = sysDeptService.deptTree();
        return Result.ok(trees);
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:user:list")
    @GetMapping(value = "/list")
    public Result list(SysUserPageVo vo) {
        IPage<SysUserPageDto> page = sysUserService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = {"/", "/{id}"})
    public Result query(@PathVariable(value = "id", required = false) String id) {
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
    @PutMapping
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
    @PostMapping
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
    @PutMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        sysUserService.changeStatus(vo);
        return Result.ok();
    }
}
