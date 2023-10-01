package cn.daenx.framework.notify.sms.service.impl;

import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.notify.sms.service.SmsService;
import com.alibaba.fastjson2.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("aliyun")
public class AliyunSmsService implements SmsService {
    /**
     * 发送短信协议，阿里云
     *
     * @param info
     * @param phones     多个手机号用,隔开
     * @param templateId
     * @param param      例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value=1234
     * @return
     */
    @Override
    public SmsSendResult sendSms(Map<String, String> info, String phones, String templateId, Map<String, String> param) {
        try {
            Config config = new Config();
            config.setAccessKeyId(info.get("accessKeyId"));
            config.setAccessKeySecret(info.get("accessKeySecret"));
            config.setEndpoint(info.get("endpoint"));
            Client client = new Client(config);
            com.aliyun.dysmsapi20170525.models.SendSmsRequest req = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setPhoneNumbers(phones)
                    .setSignName(info.get("signName"))
                    .setTemplateCode(templateId)
                    .setTemplateParam(JSON.toJSONString(param));
            SendSmsResponse resp = client.sendSms(req);
            SmsSendResult smsSendResult = SmsSendResult.builder().isSuccess("OK".equals(resp.getBody().getCode())).msg(resp.getBody().getMessage()).aliyunRes(resp).build();
            return smsSendResult;
        } catch (Exception e) {
            e.printStackTrace();
            SmsSendResult smsSendResult = SmsSendResult.builder().isSuccess(false).msg(e.getMessage()).build();
            return smsSendResult;
        }
    }
}
