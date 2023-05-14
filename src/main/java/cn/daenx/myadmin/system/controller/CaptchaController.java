package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.utils.DingTalkUtil;
import cn.daenx.myadmin.common.utils.EmailUtil;
import cn.daenx.myadmin.common.utils.SmsUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.daenx.myadmin.system.vo.system.DingTalkSendResult;
import cn.daenx.myadmin.system.vo.system.SmsSendResult;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 测试邮箱机制
     *
     * @return
     */
    @GetMapping("/testEmail")
    public Result testEmail() {
        Boolean aBoolean = EmailUtil.sendEmail("1330166565@qq.com", "测试", "阿萨德", false, null);
        return Result.ok(aBoolean);
    }

    /**
     * 测试短信
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
        SmsSendResult smsSendResult = SmsUtil.sendSms("+8618731055555,+8618754158888", "SMS_460755481", map2, "aliyun");
        return Result.ok(smsSendResult);
    }

    /**
     * 测试钉钉群通知
     *
     * @return
     */
    @GetMapping("/testDingTalk")
    public Result testDingTalk() {
        //发送普通文本消息
//        List<DingTalkSendResult> dingTalkSendResults = DingTalkUtil.sendTalk("testbot", "测试普通文本消息");
        //自己组装复杂的消息，以便发送其他消息类型的消息
        List<DingTalkSendResult> dingTalkSendResults = DingTalkUtil.sendTalkContent("testbot", "{\"msgtype\":\"markdown\",\"markdown\":{\"title\":\"杭州天气\",\"text\":\"#### 杭州天气 @150XXXXXXXX \\n > 9度，西北风1级，空气良89，相对温度73%\\n > ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\\n > ###### 10点20分发布 [天气](https://www.dingtalk.com) \\n\"},\"at\":{\"atMobiles\":[\"150XXXXXXXX\"],\"atUserIds\":[\"user123\"],\"isAtAll\":false}}");
        return Result.ok(dingTalkSendResults);
    }

}
