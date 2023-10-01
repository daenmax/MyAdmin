package cn.daenx.framework.common.vo.system.other;

import cn.daenx.framework.common.vo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务调度表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysJobVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 任务状态，0=正常，1=暂停
     */
    private String status;

    /**
     * 计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行
     */
    private String misfirePolicy;

    /**
     * 是否并发执行，0=允许，1=禁止
     */
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
     * 下次执行时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextValidTime;
    private String errorMsg;

}
