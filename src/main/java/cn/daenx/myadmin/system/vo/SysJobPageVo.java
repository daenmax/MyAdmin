package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;


@Data
public class SysJobPageVo extends BasePageVo {

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

}
