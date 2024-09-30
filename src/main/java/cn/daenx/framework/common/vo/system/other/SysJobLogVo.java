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
 * 定时任务调度日志表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysJobLogVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 关联定时任务ID
     */
    private String jobId;

    /**
     * 日志信息
     */
    private String jobMessage;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 开始执行时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 结束执行时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 执行耗时时间（毫秒）
     */
    private Integer executeTime;

    /**
     * 执行结果，0=成功，1=失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
