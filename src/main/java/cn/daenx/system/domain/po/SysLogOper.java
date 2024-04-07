package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.serializer.annotation.Dict;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log_oper")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysLogOper extends BaseEntity implements Serializable {
    @ExcelProperty(value = "操作ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 操作名称
     */
    @ExcelProperty(value = "操作名称")
    @TableField(value = "name")
    private String name;

    /**
     * 操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出，7=上传，8=下载
     */
    @ExcelProperty(value = "操作类型", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_oper_type", custom = {})
    @TableField(value = "type")
    private String type;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    @TableField(value = "method")
    private String method;

    /**
     * 请求方式
     */
    @ExcelProperty(value = "请求方式")
    @TableField(value = "request_type")
    private String requestType;

    /**
     * 请求URL
     */
    @ExcelProperty(value = "请求URL")
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 请求者IP
     */
    @ExcelProperty(value = "请求者IP")
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 请求者地址
     */
    @ExcelProperty(value = "请求者地址")
    @TableField(value = "request_location")
    private String requestLocation;

    /**
     * 请求参数
     */
    @ExcelProperty(value = "请求参数")
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 请求时间
     */
    @ExcelProperty(value = "请求时间")
    @TableField(value = "request_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime requestTime;

    /**
     * 响应结果
     */
    @ExcelProperty(value = "响应结果")
    @TableField(value = "response_result")
    private String responseResult;

    /**
     * 响应时间
     */
    @ExcelProperty(value = "响应时间")
    @TableField(value = "response_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime responseTime;

    /**
     * 错误信息
     */
    @ExcelProperty(value = "错误信息")
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 耗时时间（毫秒）
     */
    @ExcelProperty(value = "耗时时间（毫秒）")
    @TableField(value = "execute_time")
    private Integer executeTime;

    /**
     * 请求结果，0=成功，1=失败
     */
    @ExcelProperty(value = "请求结果", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_common_status", custom = {})
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否删除，0=正常，1=删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

}
