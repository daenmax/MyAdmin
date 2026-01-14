package cn.daenx.server.api.admin.base;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.domain.vo.RouterVo;
import cn.daenx.modules.system.domain.dto.sysUser.SysUserUpdBindDto;
import cn.daenx.modules.system.domain.dto.sysUser.SysUserUpdInfoDto;
import cn.daenx.modules.system.domain.dto.sysUser.SysUserUpdPwdDto;
import cn.daenx.modules.system.service.LoginService;
import cn.daenx.modules.system.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心
 */
@RestController
@RequestMapping("/userCenter")
public class UserCenterController {
    @Resource
    private LoginService loginService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户详细信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public Result<Map<String, Object>> getInfo() {
        Map<String, Object> map = loginService.getInfo();
        return Result.ok(map);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public Result<List<RouterVo>> getRouters() {
        List<RouterVo> routers = loginService.getRouters();
        return Result.ok(routers);
    }

    /**
     * 个人信息
     *
     * @return
     */
    @GetMapping("/getProfile")
    public Result<Map<String, Object>> getProfile() {
        Map<String, Object> map = sysUserService.profile();
        return Result.ok(map);
    }

    /**
     * 修改个人资料
     *
     * @return
     */
    @PostMapping("/editProfile")
    public Result<Void> edit(@Validated @RequestBody SysUserUpdInfoDto dto) {
        sysUserService.updInfo(dto);
        return Result.ok();
    }

    /**
     * 修改个人密码
     *
     * @return
     */
    @PostMapping("/editPwd")
    public Result<Void> editPwd(@Validated @RequestBody SysUserUpdPwdDto dto) {
        sysUserService.updatePwd(dto);
        return Result.ok("修改成功，请重新登录", null);
    }

    /**
     * 修改头像
     *
     * @return
     */
    @PostMapping("/editAvatar")
    public Result<Map<String, Object>> editAvatar(@RequestPart("avatar") MultipartFile file) {
        String imgUrl = sysUserService.avatar(file);
        Map<String, Object> map = new HashMap<>();
        map.put("imgUrl", imgUrl);
        return Result.ok(map);
    }


    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @PostMapping("/getEmailValidCode")
    public Result<Map<String, Object>> getEmailValidCode(@Validated @RequestBody SysUserUpdBindDto dto) {
        loginService.validatedCaptcha(dto);
        Map<String, Object> map = sysUserService.getEmailValidCode(dto);
        return Result.ok(map);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @PostMapping("/getPhoneValidCode")
    public Result<Map<String, Object>> getPhoneValidCode(@Validated @RequestBody SysUserUpdBindDto dto) {
        loginService.validatedCaptcha(dto);
        Map<String, Object> map = sysUserService.getPhoneValidCode(dto);
        return Result.ok(map);
    }


    /**
     * 修改邮箱绑定
     *
     * @return
     */
    @PostMapping("/updateBindEmail")
    public Result<Map<String, Object>> updateBindEmail(@Validated @RequestBody SysUserUpdBindDto dto) {
        Map<String, Object> map = sysUserService.updateBindEmail(dto);
        return Result.ok(map);
    }

    /**
     * 修改手机绑定
     *
     * @return
     */
    @PostMapping("/updateBindPhone")
    public Result<Map<String, Object>> updateBindPhone(@Validated @RequestBody SysUserUpdBindDto dto) {
        Map<String, Object> map = sysUserService.updateBindPhone(dto);
        return Result.ok(map);
    }
}
