package cn.daenx.admin.controller.system;

import cn.daenx.framework.notify.dingTalk.utils.DingTalkUtil;
import cn.daenx.framework.notify.dingTalk.vo.DingTalkSendResult;
import cn.daenx.framework.notify.dingTalk.vo.SendDingTalkVo;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.notify.email.vo.SendEmailVo;
import cn.daenx.framework.notify.feishu.utils.FeishuUtil;
import cn.daenx.framework.notify.feishu.vo.FeishuSendResult;
import cn.daenx.framework.notify.feishu.vo.SendFeishuVo;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.common.utils.MyUtil;
import cn.daenx.common.vo.Result;
import cn.daenx.framework.notify.sms.vo.SendSmsVo;
import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.notify.wecom.utils.WecomUtil;
import cn.daenx.framework.notify.wecom.vo.SendWecomVo;
import cn.daenx.framework.notify.wecom.vo.WecomSendResult;
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
        SmsSendResult result = SmsUtil.sendSms(MyUtil.join(vo.getPhones(), String::trim, ","), vo.getTemplateId(), smsMap, vo.getType());
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
    public Result sendDingTalk(@Validated @RequestBody SendDingTalkVo vo) {
        //发送普通文本消息
        List<DingTalkSendResult> result = DingTalkUtil.sendMsg(vo.getBotName(), vo.getMsg());
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
    public Result sendFeishu(@Validated @RequestBody SendFeishuVo vo) {
        //发送普通文本消息
        List<FeishuSendResult> result = FeishuUtil.sendMsg(vo.getBotName(), vo.getMsg());
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
    public Result sendWecom(@Validated @RequestBody SendWecomVo vo) {
        //发送普通文本消息
        List<WecomSendResult> result = WecomUtil.sendMsg(vo.getBotName(), vo.getMsg());
        if (result.get(0).isSuccess()) {
            return Result.ok("发送成功", null);
        }
        return Result.error(result.get(0).getMsg());
    }
}
