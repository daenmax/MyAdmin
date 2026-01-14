package cn.daenx.server.api.admin.tool;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.notify.dingTalk.domain.DingTalkSendResult;
import cn.daenx.framework.notify.dingTalk.domain.SendDingTalkDto;
import cn.daenx.framework.notify.dingTalk.utils.DingTalkUtil;
import cn.daenx.framework.notify.email.domain.SendEmailDto;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.notify.feishu.domain.FeishuSendResult;
import cn.daenx.framework.notify.feishu.domain.SendFeishuDto;
import cn.daenx.framework.notify.feishu.utils.FeishuUtil;
import cn.daenx.framework.notify.sms.domain.SendSmsDto;
import cn.daenx.framework.notify.sms.domain.SmsSendResult;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.framework.notify.wecom.domain.SendWecomDto;
import cn.daenx.framework.notify.wecom.domain.WecomSendResult;
import cn.daenx.framework.notify.wecom.utils.WecomUtil;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Result<String> sendEmail(@Validated @RequestBody SendEmailDto dto) {
        Boolean res;
        if (ObjectUtil.isEmpty(dto.getFormEmail())) {
            res = EmailUtil.sendEmail(dto.getToEmail(), dto.getSubject(), dto.getContent(), false, null);
        } else {
            res = EmailUtil.sendEmail(dto.getToEmail(), dto.getSubject(), dto.getContent(), false, null, dto.getFormEmail());
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
    public Result<String> sendSms(@Validated @RequestBody SendSmsDto dto) {
        Map<String, String> smsMap = new HashMap<>();
        for (String kv : dto.getKv()) {
            String[] split = kv.split("-");
            if (split.length == 2) {
                smsMap.put(split[0], split[1]);
            }
        }
        SmsSendResult result = SmsUtil.sendSms(MyUtil.join(dto.getPhones(), String::trim, ","), dto.getTemplateId(), smsMap, dto.getType());
        if (result.isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(result.getMsg());
    }

    /**
     * 钉钉测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendDingTalk")
    @PostMapping("/sendDingTalk")
    public Result<String> sendDingTalk(@Validated @RequestBody SendDingTalkDto dto) {
        //发送普通文本消息
        List<DingTalkSendResult> result = DingTalkUtil.sendMsg(dto.getBotName(), dto.getMsg());
        if (result.get(0).isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(result.get(0).getMsg());
    }

    /**
     * 飞书测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendFeishu")
    @PostMapping("/sendFeishu")
    public Result<String> sendFeishu(@Validated @RequestBody SendFeishuDto dto) {
        //发送普通文本消息
        List<FeishuSendResult> result = FeishuUtil.sendMsg(dto.getBotName(), dto.getMsg());
        if (result.get(0).isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(result.get(0).getMsg());
    }

    /**
     * 企业微信测试
     *
     * @return
     */
    @SaCheckPermission("tool:functest:sendWecom")
    @PostMapping("/sendWecom")
    public Result<String> sendWecom(@Validated @RequestBody SendWecomDto dto) {
        //发送普通文本消息
        List<WecomSendResult> result = WecomUtil.sendMsg(dto.getBotName(), dto.getMsg());
        if (result.get(0).isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(result.get(0).getMsg());
    }
}
