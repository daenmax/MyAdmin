package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.utils.SmsUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.daenx.myadmin.system.service.SysConfigService;
import cn.daenx.myadmin.system.vo.system.SmsSendResult;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SaIgnore
@RestController
@RequestMapping("")
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SysConfigService sysConfigService;

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
     * 测试邮箱机制
     *
     * @return
     */
    @GetMapping("/testEmail")
    public Result testEmail() {
//        SysEmailConfigVo.Email email = EmailUtil.getOneEmailConfig();
//        System.out.println(email.getEmail());
        return Result.ok("");
    }
    /**
     * 测试邮箱机制
     *
     * @return
     */
    @GetMapping("/testSms")
    public Result testSms() {
        //腾讯云
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("1", "1234");
//        map1.put("2", "8888");
//        SmsSendResult smsSendResult = SmsUtil.sendSms("+8618731055555,+8618754158888", "1794869", map1,"tencent");
        //阿里云
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "6666");
        SmsSendResult smsSendResult = SmsUtil.sendSms("+8618731055555,+8618754158888", "SMS_460755481", map2,"aliyun");
        return Result.ok(smsSendResult);
    }

}
