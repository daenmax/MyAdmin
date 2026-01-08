package cn.daenx.server.api.admin.base;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.modules.system.domain.dto.sysLogin.SysLoginDto;
import cn.daenx.modules.system.domain.dto.sysLogin.SysRegisterDto;
import cn.daenx.modules.system.service.LoginService;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 获取人机验证码
     *
     * @return
     */
    @SaIgnore
    @GetMapping("/captcha")
    public Result captcha() {
        HashMap<String, Object> map = loginService.createCaptcha();
        return Result.ok(map);
    }

    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getEmailValidCode")
    public Result getEmailValidCode(@Validated @RequestBody SysLoginDto vo) {
        return loginService.getEmailValidCode(vo);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getPhoneValidCode")
    public Result getPhoneValidCode(@Validated @RequestBody SysLoginDto vo) {
        return loginService.getPhoneValidCode(vo);
    }

    /**
     * PC登录
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public Result login(@Validated @RequestBody SysLoginDto vo) {
        if (ObjectUtil.isEmpty(vo.getLoginType())) {
            return Result.error("loginType不能为空");
        }
        String token = loginService.login(vo);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }
    //APP登录
    //微信小程序登录
    //开放API登录

    /**
     * 通用注册接口
     * 只接受账号和密码
     * 手机号、邮箱、openid需要另外单独绑定
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/register")
    public Result register(@Validated @RequestBody SysRegisterDto vo) {
        loginService.register(vo);
        return Result.ok("注册成功");
    }


    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public Result logout() {
        loginService.logout();
        return Result.ok("退出成功");
    }
}
