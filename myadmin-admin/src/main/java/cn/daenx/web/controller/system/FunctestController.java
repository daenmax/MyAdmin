package cn.daenx.web.controller.system;

import cn.daenx.framework.common.utils.DingTalkUtil;
import cn.daenx.framework.common.utils.EmailUtil;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.SmsUtil;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.common.vo.system.utils.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SaIgnore
@RestController
@RequestMapping("/tool/functest")
public class FunctestController {


    /**
     * 邮箱测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendEmail")
    @PostMapping("/sendEmail")
    public Result sendEmail(@Validated @RequestBody SendEmailVo vo) {
        Boolean res;
        if (ObjectUtil.isEmpty(vo.getFormEmail())) {
            res = EmailUtil.sendEmail(vo.getToEmail(), vo.getSubject(), vo.getContent(), false, null);
        } else {
            res = EmailUtil.sendEmail(vo.getToEmail(), vo.getSubject(), vo.getContent(), false, null, vo.getFormEmail());
        }
        return res ? Result.ok("发送成功", null) : Result.error("发送失败");
    }

    /**
     * 短信测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendSms")
    @PostMapping("/sendSms")
    public Result sendSms(@Validated @RequestBody SendSmsVo vo) {
        Map<String, String> smsMap = new HashMap<>();
        for (String kv : vo.getKv()) {
            String[] split = kv.split("-");
            if (split.length == 2) {
                smsMap.put(split[0], split[1]);
            }
        }
        SmsSendResult smsSendResult = SmsUtil.sendSms(MyUtil.join(vo.getPhones(), String::trim, ","), vo.getTemplateId(), smsMap, vo.getType());
        if (smsSendResult.isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(smsSendResult.getMsg());
    }

    /**
     * 钉钉测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendDingTalk")
    @PostMapping("/sendDingTalk")
    public Result sendDingTalk(@Validated @RequestBody SendDingTalkVo vo) {
        //发送普通文本消息
        List<DingTalkSendResult> dingTalkSendResults = DingTalkUtil.sendTalk(vo.getBotName(), vo.getMsg());
        if (dingTalkSendResults.get(0).isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(dingTalkSendResults.get(0).getMsg());
    }

}
