package cn.daenx.admin.controller.system;

import cn.daenx.common.vo.Result;
import cn.daenx.common.vo.RouterVo;
import cn.daenx.system.domain.vo.SysLoginVo;
import cn.daenx.system.domain.vo.SysRegisterVo;
import cn.daenx.system.service.SysLoginService;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class SysLoginController {
    @Resource
    private SysLoginService sysLoginService;

    /**
     * 获取人机验证码
     *
     * @return
     */
    @SaIgnore
    @GetMapping("/captcha")
    public Result captcha() {
        HashMap<String, Object> map = sysLoginService.createCaptcha();
        return Result.ok(map);
    }

    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getEmailValidCode")
    public Result getEmailValidCode(@Validated @RequestBody SysLoginVo vo) {
        return sysLoginService.getEmailValidCode(vo);
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @SaIgnore
    @PostMapping("/getPhoneValidCode")
    public Result getPhoneValidCode(@Validated @RequestBody SysLoginVo vo) {
        return sysLoginService.getPhoneValidCode(vo);
    }

    /**
     * PC登录
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public Result login(@Validated @RequestBody SysLoginVo vo) {
        if (ObjectUtil.isEmpty(vo.getLoginType())) {
            return Result.error("loginType不能为空");
        }
        String token = sysLoginService.login(vo);
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
    public Result register(@Validated @RequestBody SysRegisterVo vo) {
        sysLoginService.register(vo);
        return Result.ok("注册成功");
    }


    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public Result logout() {
        sysLoginService.logout();
        return Result.ok("退出成功");
    }

    @GetMapping("/getInfo")
    public Result getInfo() {
        Map<String, Object> map = sysLoginService.getInfo();
        return Result.ok(map);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public Result getRouters() {
        List<RouterVo> routers = sysLoginService.getRouters();
        return Result.ok(routers);
    }
}
