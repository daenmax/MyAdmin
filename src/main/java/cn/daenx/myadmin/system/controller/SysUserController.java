package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.domain.dto.SysUserPageDto;
import cn.daenx.myadmin.system.domain.vo.*;
import cn.daenx.myadmin.system.service.SysDeptService;
import cn.daenx.myadmin.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
    @PostMapping("/editProfile")
    public Result edit(@Validated @RequestBody SysUserUpdInfoVo vo) {
        sysUserService.updInfo(vo);
        return Result.ok();
    }

    /**
     * 修改个人密码
     *
     * @return
     */
    @PostMapping("/updatePwd")
    public Result updatePwd(@Validated @RequestBody SysUserUpdPwdVo vo) {
        sysUserService.updatePwd(vo);
        return Result.ok("修改成功，请重新登录", null);
    }

    /**
     * 修改头像
     *
     * @return
     */
    @PostMapping("/profile/avatar")
    public Result avatar(@RequestPart("avatar") MultipartFile file) {
        String imgUrl = sysUserService.avatar(file);
        Map<String, Object> map = new HashMap<>();
        map.put("imgUrl", imgUrl);
        return Result.ok(map);
    }

    /**
     * 获取部门树列表
     *
     * @return
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    public Result deptTree() {
        List<Tree<String>> trees = sysDeptService.deptTree(new SysDeptPageVo());
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
     * 导出
     */
    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    public void export(SysUserPageVo vo, HttpServletResponse response) {
        List<SysUserPageDto> list = sysUserService.getAll(vo);
        ExcelUtil.exportXlsx(response, "用户", "系统用户", list, SysUserPageDto.class);
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
    @SaCheckPermission("system:user:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
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


    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @PostMapping("/getEmailValidCode")
    public Result getEmailValidCode(@Validated @RequestBody SysUserUpdBindVo vo) {
        return sysUserService.getEmailValidCode(vo);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @PostMapping("/getPhoneValidCode")
    public Result getPhoneValidCode(@Validated @RequestBody SysUserUpdBindVo vo) {
        return sysUserService.getPhoneValidCode(vo);
    }


    /**
     * 修改邮箱绑定
     *
     * @return
     */
    @PostMapping("/updateBindEmail")
    public Result updateBindEmail(@Validated @RequestBody SysUserUpdBindVo vo) {
        return sysUserService.updateBindEmail(vo);
    }

    /**
     * 修改手机绑定
     *
     * @return
     */
    @PostMapping("/updateBindPhone")
    public Result updateBindPhone(@Validated @RequestBody SysUserUpdBindVo vo) {
        return sysUserService.updateBindPhone(vo);
    }
}
