package cn.daenx.myadmin.system.po;

import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
    * 操作日志表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log_oper")
public class SysLogOper extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 操作名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 方法名
     */
    @TableField(value = "`method`")
    private String method;

    /**
     * 请求方式
     */
    @TableField(value = "request_type")
    private String requestType;

    /**
     * 请求URL
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 请求者IP
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 请求者地址
     */
    @TableField(value = "request_location")
    private String requestLocation;

    /**
     * 请求参数
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 请求时间
     */
    @TableField(value = "request_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime requestTime;

    /**
     * 响应结果
     */
    @TableField(value = "response_result")
    private String responseResult;

    /**
     * 响应时间
     */
    @TableField(value = "response_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime responseTime;

    /**
     * 错误信息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 耗时时间
     */
    @TableField(value = "execute_time")
    private String executeTime;

    /**
     * 请求结果，0=成功，1=失败
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}