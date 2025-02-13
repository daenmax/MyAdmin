package cn.daenx.server.api.admin.base;

import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.common.vo.RouterVo;
import cn.daenx.system.domain.vo.*;
import cn.daenx.system.service.LoginService;
import cn.daenx.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Result getInfo() {
        Map<String, Object> map = loginService.getInfo();
        return Result.ok(map);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public Result getRouters() {
        List<RouterVo> routers = loginService.getRouters();
        return Result.ok(routers);
    }

    /**
     * 个人信息
     *
     * @return
     */
    @GetMapping("/getProfile")
    public Result getProfile() {
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
    @PostMapping("/editPwd")
    public Result editPwd(@Validated @RequestBody SysUserUpdPwdVo vo) {
        sysUserService.updatePwd(vo);
        return Result.ok("修改成功，请重新登录", null);
    }

    /**
     * 修改头像
     *
     * @return
     */
    @PostMapping("/editAvatar")
    public Result editAvatar(@RequestPart("avatar") MultipartFile file) {
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
    public Result getEmailValidCode(@Validated @RequestBody SysUserUpdBindVo vo) {
        loginService.validatedCaptcha(vo);
        return sysUserService.getEmailValidCode(vo);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @PostMapping("/getPhoneValidCode")
    public Result getPhoneValidCode(@Validated @RequestBody SysUserUpdBindVo vo) {
        loginService.validatedCaptcha(vo);
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
