package cn.daenx.framework.notify.sms.service;

import cn.daenx.framework.common.vo.system.utils.SmsSendResult;

import java.util.Map;

/**
 * 短信接口
 */
public interface SmsService {
    SmsSendResult sendSms(Map<String, String> info, String phones, String templateId, Map<String, String> param);
}
