package cn.daenx.admin.controller.system;

import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.service.CaptchaService;
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

}
