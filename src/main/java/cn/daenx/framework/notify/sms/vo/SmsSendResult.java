package cn.daenx.framework.notify.sms.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsSendResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    private String msg;

    /**
     * 阿里云的原始结果
     */
    private com.aliyun.dysmsapi20170525.models.SendSmsResponse aliyunRes;
    /**
     * 腾讯云的原始结果
     */
    private com.tencentcloudapi.sms.v20190711.models.SendSmsResponse tencentRes;
}
