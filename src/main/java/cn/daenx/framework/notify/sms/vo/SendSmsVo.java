package cn.daenx.framework.notify.sms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 短信测试
 */
@Data
public class SendSmsVo {
    /**
     * 使用平台：aliyun=阿里云，tencent=腾讯云
     */
    private String type;

    /**
     * 接收手机号
     */
    @Size(min = 1, message = "接收手机号最少填写一个")
    private List<String> phones;

    /**
     * 模板ID
     */
    @NotBlank(message = "模板ID不能为空")
    private String templateId;

    /**
     * kv
     */
    @Size(min = 1, message = "kv参数最少填写一个")
    private List<String> kv;

}
