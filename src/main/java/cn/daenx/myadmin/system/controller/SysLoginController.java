package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.vo.system.RouterVo;
import cn.daenx.myadmin.system.vo.system.SysLoginVo;
import cn.daenx.myadmin.system.vo.system.SysRegisterVo;
import cn.dev33.satoken.annotation.SaIgnore;
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
     * PC登录
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public Result login(@RequestBody @Validated SysLoginVo vo) {
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
    public Result register(@RequestBody @Validated SysRegisterVo vo) {
        sysLoginService.register(vo);
        return Result.ok("注册成功");
    }

    /**
     * 绑定手机号
     */

    /**
     * 绑定邮箱
     */

    /**
     * 绑定openid
     */

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
