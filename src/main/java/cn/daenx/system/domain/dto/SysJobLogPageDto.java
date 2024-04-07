package cn.daenx.system.domain.dto;

import cn.daenx.framework.common.vo.BaseDto;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.serializer.annotation.Dict;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 定时任务调度日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysJobLogPageDto extends BaseDto {
    private String id;

    /**
     * 关联定时任务ID
     */
    private String jobId;
    @ExcelProperty(value = "定时任务名称")
    private String jobName;

    /**
     * 调用目标字符串
     */
    @ExcelProperty(value = "调用目标字符串")
    private String invokeTarget;

    /**
     * 日志信息
     */
    @ExcelProperty(value = "日志信息")
    private String jobMessage;

    /**
     * 异常信息
     */
    @ExcelProperty(value = "异常信息")
    private String exceptionInfo;

    /**
     * 开始执行时间
     */
    @ExcelProperty(value = "开始执行时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 结束执行时间
     */
    @ExcelProperty(value = "结束执行时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 执行耗时时间（毫秒）
     */
    @ExcelProperty(value = "执行耗时时间（毫秒）")
    private Integer executeTime;

    /**
     * 执行结果，0=成功，1=失败
     */
    @ExcelProperty(value = "执行结果", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_common_status", custom = {})
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
