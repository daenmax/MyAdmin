package cn.daenx.framework.common.vo.other;

import cn.daenx.framework.common.vo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志表
 */
@Data
public class SysLogOperVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出，7=上传，8=下载
     */
    private String type;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求者IP
     */
    private String requestIp;

    /**
     * 请求者地址
     */
    private String requestLocation;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime requestTime;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 响应时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime responseTime;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 耗时时间（毫秒）
     */
    private Integer executeTime;

    /**
     * 请求结果，0=成功，1=失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除，0=正常，1=删除
     */
    private Integer isDelete;

}
