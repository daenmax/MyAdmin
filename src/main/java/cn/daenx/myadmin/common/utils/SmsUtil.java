package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.system.SmsSendResult;
import cn.daenx.myadmin.system.vo.system.SysSmsConfigVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sms短信工具类
 *
 * @author DaenMax
 */
public class SmsUtil {


    /**
     * 发送短信
     * <p>
     * <h4>关于phones参数</h4><br>
     * 腾讯云：格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号<br><br>
     * 阿里云：国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1390000****。国际/港澳台消息：国际区号+号码，例如852000012****。单次上限为1000个手机号码
     * </p>
     * <p>
     * <h4>关于param参数</h4><br>
     * 腾讯云：例如模板为：验证码为：{1}，您正在进行{2}，如非本人操作，请忽略本短信。那么key=1，value为1234，key=2，value=找回密码<br><br>
     * 阿里云：例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value为1234
     * </p>
     *
     * @param phones     多个手机号用,隔开
     * @param templateId
     * @param param
     * @return
     */
    public static SmsSendResult sendSms(String phones, String templateId, Map<String, String> param) {
        if (ObjectUtil.isEmpty(phones)) {
            return SmsSendResult.builder().isSuccess(false).msg("手机号不能为空").build();
        }
        if (ObjectUtil.isEmpty(templateId)) {
            return SmsSendResult.builder().isSuccess(false).msg("模板ID不能为空").build();
        }
        SysSmsConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        if (ObjectUtil.isEmpty(sysSmsConfigVo)) {
            return SmsSendResult.builder().isSuccess(false).msg("系统短信配置不可用").build();
        }
        if ("aliyun".equals(sysSmsConfigVo.getConfig().getType())) {
            return sendSmsAliyun(sysSmsConfigVo.getAliyun(), phones, templateId, param);
        } else if ("tencent".equals(sysSmsConfigVo.getConfig().getType())) {
            return sendSmsTencent(sysSmsConfigVo.getTencent(), phones, templateId, param);
        }
        return SmsSendResult.builder().isSuccess(false).msg("未知的type").build();
    }

    /**
     * 发送短信
     * <p>
     * <h4>关于phones参数</h4><br>
     * 腾讯云：格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号<br><br>
     * 阿里云：国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1390000****。国际/港澳台消息：国际区号+号码，例如852000012****。单次上限为1000个手机号码
     * </p>
     * <p>
     * <h4>关于param参数</h4><br>
     * 腾讯云：例如模板为：验证码为：{1}，您正在进行{2}，如非本人操作，请忽略本短信。那么key=1，value为1234，key=2，value=找回密码<br><br>
     * 阿里云：例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value为1234
     * </p>
     *
     * @param phones     多个手机号用,隔开
     * @param templateId
     * @param param
     * @param type       指定平台，aliyun=阿里云，tencent=腾讯云
     * @return
     */
    public static SmsSendResult sendSms(String phones, String templateId, Map<String, String> param, String type) {
        if (ObjectUtil.isEmpty(phones)) {
            return SmsSendResult.builder().isSuccess(false).msg("手机号不能为空").build();
        }
        if (ObjectUtil.isEmpty(templateId)) {
            return SmsSendResult.builder().isSuccess(false).msg("模板ID不能为空").build();
        }
        if (ObjectUtil.isEmpty(type)) {
            return SmsSendResult.builder().isSuccess(false).msg("type不能为空").build();
        }
        SysSmsConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        if (ObjectUtil.isEmpty(sysSmsConfigVo)) {
            return SmsSendResult.builder().isSuccess(false).msg("系统短信配置不可用").build();
        }
        if ("aliyun".equals(type)) {
            return sendSmsAliyun(sysSmsConfigVo.getAliyun(), phones, templateId, param);
        } else if ("tencent".equals(type)) {
            return sendSmsTencent(sysSmsConfigVo.getTencent(), phones, templateId, param);
        }
        return SmsSendResult.builder().isSuccess(false).msg("未知的type").build();
    }

    /**
     * 发送短信协议，阿里云
     *
     * @param smsInfo
     * @param phones     多个手机号用,隔开，需要加+86等前缀
     * @param templateId
     * @param param      例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value为1234
     * @return
     */
    private static SmsSendResult sendSmsAliyun(SysSmsConfigVo.SmsInfo smsInfo, String phones, String templateId, Map<String, String> param) {
        try {
            Config config = new Config();
            config.setAccessKeyId(smsInfo.getAccessKeyId());
            config.setAccessKeySecret(smsInfo.getAccessKeySecret());
            config.setEndpoint(smsInfo.getEndpoint());
            Client client = new Client(config);
            com.aliyun.dysmsapi20170525.models.SendSmsRequest req = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setPhoneNumbers(phones)
                    .setSignName(smsInfo.getSignName())
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

    /**
     * 发送短信协议，腾讯云
     *
     * @param smsInfo
     * @param phones     多个手机号用,隔开，需要加+86等前缀
     * @param templateId
     * @param param      例如模板为：验证码为：{1}，您正在进行{2}，如非本人操作，请忽略本短信。那么key=1，value为1234，key=2，value=找回密码
     * @return
     */
    private static SmsSendResult sendSmsTencent(SysSmsConfigVo.SmsInfo smsInfo, String phones, String templateId, Map<String, String> param) {
        try {
            Credential credential = new Credential(smsInfo.getAccessKeyId(), smsInfo.getAccessKeySecret());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(smsInfo.getEndpoint());
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
            req.setSign(smsInfo.getSignName());
            req.setSmsSdkAppid(smsInfo.getSdkAppId());
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

    /**
     * 从redis里获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysSmsConfigVo getSysSmsConfigVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.sms.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return null;
        }
        SysSmsConfigVo sysSmsConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysSmsConfigVo.class);
        return sysSmsConfigVo;
    }

}
