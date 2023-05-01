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
    * 定时任务调度日志表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_job_log")
public class SysJobLog extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 关联定时任务ID
     */
    @TableField(value = "job_id")
    private String jobId;

    /**
     * 日志信息
     */
    @TableField(value = "job_message")
    private String jobMessage;

    /**
     * 异常信息
     */
    @TableField(value = "exception_info")
    private String exceptionInfo;

    /**
     * 参数状态，0=成功，1=失败
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    public static final String COL_ID = "id";

    public static final String COL_JOB_ID = "job_id";

    public static final String COL_JOB_MESSAGE = "job_message";

    public static final String COL_EXCEPTION_INFO = "exception_info";

    public static final String COL_STATUS = "status";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATE_ID = "create_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_ID = "update_id";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_IS_DELETE = "is_delete";
}