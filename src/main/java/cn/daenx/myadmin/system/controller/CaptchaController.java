package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.*;
import cn.daenx.myadmin.common.vo.CheckSendVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.CaptchaService;
import cn.daenx.myadmin.system.vo.system.DingTalkSendResult;
import cn.daenx.myadmin.system.vo.system.SmsSendResult;
import cn.daenx.myadmin.system.vo.system.SysSendLimitConfigVo;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    /**
     * 测试发送限制
     *
     * @return
     */
    @GetMapping("/testLimit")
    public Result testLimit() {
        CheckSendVo checkSendVo = checkSendByUserId("1111111");
        if (checkSendVo.getNowOk()) {
            Integer integer = saveSendByUserId("1111111");
            return Result.ok(integer);
        }
        return Result.error("NO", checkSendVo);
    }

    public static CheckSendVo checkSendByUserId(String userId) {
        String key = userId;
        Integer dayMax = 3;
        Integer needWait = 30;

        //判断今天是否还可以发送
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            String str = MyUtil.timeDistance(Long.valueOf(String.valueOf(remainSecondsOneDay * 1000)));
            return new CheckSendVo(false, remainSecondsOneDay, "今日请求过多，请于" + str + "后再试");
        }
        String lastSendTimeStr = (String) RedisUtil.getValue(RedisConstant.SEND_EMAIL + key);
        if (ObjectUtil.isEmpty(lastSendTimeStr)) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        LocalDateTime lastSendTime = MyUtil.strToLocalDateTime(lastSendTimeStr, "yyyy-MM-dd HH:mm:ss");
        Integer diffSec = MyUtil.getDiffSec(LocalDateTime.now(), lastSendTime);
        if (diffSec < needWait) {
            int sec = needWait - diffSec;
            String str = MyUtil.timeDistance(Long.valueOf(String.valueOf(sec * 1000)));
            return new CheckSendVo(false, sec, "请求过于频繁，请于" + str + "后再试");
        }
        return new CheckSendVo(true, 0, "可以进行发送");
    }

    public static Integer saveSendByUserId(String userId) {
        String key = userId;
        Integer dayMax = 3;
        Integer needWait = 30;
        String dateStrByFormat = MyUtil.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setValue(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + ":" + IdUtil.fastSimpleUUID(), dateStrByFormat, 1L, TimeUnit.DAYS);
        RedisUtil.setValue(RedisConstant.SEND_EMAIL + key, dateStrByFormat);
        //计算下次可以发的秒数
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            return remainSecondsOneDay;
        }
        return needWait;
    }
}
