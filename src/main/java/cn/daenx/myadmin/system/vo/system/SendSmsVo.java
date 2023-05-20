package cn.daenx.myadmin.system.vo.system;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    @NotBlank(message = "接收手机号不能为空")
    private String phones;

    /**
     * 模板ID
     */
    @NotBlank(message = "模板ID不能为空")
    private String templateId;

    /**
     * kv
     */
    @NotBlank(message = "kv不能为空")
    private String kv;

}
