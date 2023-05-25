package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SaIgnore
@RestController
@RequestMapping("")
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public Result captcha() {
        HashMap<String, Object> map = captchaService.createCaptcha();
        return Result.ok(map);
    }

    /**
     * 测试接口
     *
     * @return
     */
    @GetMapping("/testApi")
    public Result testApi() {
        return Result.ok();
    }

}
