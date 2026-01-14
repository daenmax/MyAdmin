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
import java.util.Map;

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
    public Result<Map<String, Object>> captcha() {
        Map<String, Object> map = loginService.createCaptcha();
        return Result.ok(map);
    }

    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getEmailValidCode")
    public Result<Map<String, Object>> getEmailValidCode(@Validated @RequestBody SysLoginDto dto) {
        Map<String, Object> map = loginService.getEmailValidCode(dto);
        return Result.ok(map);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getPhoneValidCode")
    public Result<Map<String, Object>> getPhoneValidCode(@Validated @RequestBody SysLoginDto dto) {
        Map<String, Object> map = loginService.getPhoneValidCode(dto);
        return Result.ok(map);
    }

    /**
     * PC登录
     *
     * @param dto
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public Result<Map<String, String>> login(@Validated @RequestBody SysLoginDto dto) {
        if (ObjectUtil.isEmpty(dto.getLoginType())) {
            return Result.error("loginType不能为空");
        }
        String token = loginService.login(dto);
        Map<String, String> map = new HashMap<>();
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
     * @param dto
     * @return
     */
    @SaIgnore
    @PostMapping("/register")
    public Result<String> register(@Validated @RequestBody SysRegisterDto dto) {
        loginService.register(dto);
        return Result.ok("注册成功");
    }


    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public Result<String> logout() {
        loginService.logout();
        return Result.ok("退出成功");
    }
}
