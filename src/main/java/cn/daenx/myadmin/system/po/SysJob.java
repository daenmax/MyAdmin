package cn.daenx.myadmin.system.po;

import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
    * 定时任务调度表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_job")
public class SysJob extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 任务名称
     */
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 任务分组
     */
    @TableField(value = "job_group")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @TableField(value = "invoke_target")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 参数状态，0=正常，1=暂停
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行
     */
    @TableField(value = "misfire_policy")
    private String misfirePolicy;

    /**
     * 是否并发执行，0=允许，1=禁止
     */
    @TableField(value = "concurrent")
    private String concurrent;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    public static final String COL_ID = "id";

    public static final String COL_JOB_NAME = "job_name";

    public static final String COL_JOB_GROUP = "job_group";

    public static final String COL_INVOKE_TARGET = "invoke_target";

    public static final String COL_CRON_EXPRESSION = "cron_expression";

    public static final String COL_STATUS = "status";

    public static final String COL_MISFIRE_POLICY = "misfire_policy";

    public static final String COL_CONCURRENT = "concurrent";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATE_ID = "create_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_ID = "update_id";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_IS_DELETE = "is_delete";
}