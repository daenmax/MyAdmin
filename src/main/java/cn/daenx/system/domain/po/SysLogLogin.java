package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.serializer.annotation.Dict;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
    * 登录日志表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log_login")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysLogLogin extends BaseEntity implements Serializable {
    @ExcelProperty(value = "登录ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    @TableField(value = "username")
    private String username;

    /**
     * 登录IP
     */
    @ExcelProperty(value = "登录IP")
    @TableField(value = "ip")
    private String ip;

    /**
     * 登录地点
     */
    @ExcelProperty(value = "登录地点")
    @TableField(value = "location")
    private String location;

    /**
     * 浏览器
     */
    @ExcelProperty(value = "浏览器")
    @TableField(value = "browser")
    private String browser;

    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统")
    @TableField(value = "os")
    private String os;

    /**
     * 登录结果，0=成功，1=失败
     */
    @ExcelProperty(value = "登录结果", converter = ExcelConverter.class)
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
