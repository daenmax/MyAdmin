package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SysJobUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

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

}
