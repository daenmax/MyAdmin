package cn.daenx.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SysJobAddVo {

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    /**
     * 任务分组
     */
    @NotBlank(message = "任务分组不能为空")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank(message = "调用目标字符串不能为空")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @NotBlank(message = "cron执行表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态，0=正常，1=暂停
     */
    @NotBlank(message = "任务状态不能为空")
    private String status;

    /**
     * 计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行
     */
    @NotBlank(message = "计划执行错误策略不能为空")
    private String misfirePolicy;

    /**
     * 是否并发执行，0=允许，1=禁止
     */
    @NotBlank(message = "是否并发执行不能为空")
    private String concurrent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 异常时，通知渠道，0=不通知，1=邮件，2=短信，3=钉钉，4=飞书，5=企业微信
     */
    private String notifyChannel;

    /**
     * 异常时，通知对象，多个用,隔开
     * 邮件渠道时，写邮箱
     * 短信渠道时，写手机号
     * 钉钉、飞书、企业微信渠道时，写botName
     */
    private String notifyObjs;

    /**
     * 通知渠道是短信时，填写变量名、模板ID和短信长度，例如：
     * {"variable":"content","templateId":"123123","length":70}
     */
    private String notifySmsInfo;

}
