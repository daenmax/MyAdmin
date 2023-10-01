package cn.daenx.framework.notify.sms.service.impl;

import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.notify.sms.service.SmsService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("tencent")
public class TencentSmsService implements SmsService {
    /**
     * 发送短信协议，腾讯云
     *
     * @param info
     * @param phones     多个手机号用,隔开，需要加+86等前缀
     * @param templateId
     * @param param      例如模板为：例如模板为：验证码为：{1}，有效期为{2}分钟，如非本人操作，请忽略本短信。那么key=1，value=6666，key=2，value=5
     * @return
     */
    @Override
    public SmsSendResult sendSms(Map<String, String> info, String phones, String templateId, Map<String, String> param) {
        try {
            Credential credential = new Credential(info.get("accessKeyId"), info.get("accessKeySecret"));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(info.get("endpoint"));
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(credential, "", clientProfile);
            com.tencentcloudapi.sms.v20190711.models.SendSmsRequest req = new SendSmsRequest();
            Set<String> set = Arrays.stream(phones.split(",")).collect(Collectors.toSet());
            req.setPhoneNumberSet(ArrayUtil.toArray(set, String.class));
            if (CollUtil.isNotEmpty(param)) {
                req.setTemplateParamSet(ArrayUtil.toArray(param.values(), String.class));
            }
            req.setTemplateID(templateId);
            req.setSign(info.get("signName"));
            req.setSmsSdkAppid(info.get("sdkAppId"));
            com.tencentcloudapi.sms.v20190711.models.SendSmsResponse resp = client.SendSms(req);
            SmsSendResult smsSendResult = SmsSendResult.builder().isSuccess(true).msg("ok").tencentRes(resp).build();
            for (SendStatus sendStatus : resp.getSendStatusSet()) {
                if (!"Ok".equals(sendStatus.getCode())) {
                    smsSendResult.setSuccess(false);
                    sendStatus.setMessage(sendStatus.getMessage());
                    break;
                }
            }
            return smsSendResult;
        } catch (Exception e) {
            e.printStackTrace();
            SmsSendResult smsSendResult = SmsSendResult.builder().isSuccess(false).msg(e.getMessage()).build();
            return smsSendResult;
        }
    }
}
