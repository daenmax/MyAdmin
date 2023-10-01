package cn.daenx.framework.notify.email.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 邮箱测试
 */
@Data
public class SendEmailVo {
    /**
     * 使用邮箱
     */
    private String formEmail;

    /**
     * 接收邮箱
     */
    @NotBlank(message = "接收邮箱不能为空")
    private String toEmail;

    /**
     * 邮件主题
     */
    @NotBlank(message = "邮件主题不能为空")
    private String subject;

    /**
     * 邮件正文
     */
    @NotBlank(message = "邮件正文不能为空")
    private String content;

}
