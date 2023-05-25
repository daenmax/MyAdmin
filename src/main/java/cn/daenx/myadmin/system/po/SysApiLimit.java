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
 * 接口限制
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_api_limit")
public class SysApiLimit extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 接口名称
     */
    @TableField(value = "api_name")
    private String apiName;

    /**
     * 接口uri
     */
    @TableField(value = "api_uri")
    private String apiUri;

    /**
     * 单个用户次数
     */
    @TableField(value = "single_frequency")
    private Integer singleFrequency;

    /**
     * 单个用户时间
     */
    @TableField(value = "single_time")
    private Integer singleTime;

    /**
     * 单个用户时间单位
     */
    @TableField(value = "single_time_unit")
    private String singleTimeUnit;

    /**
     * 全部用户次数
     */
    @TableField(value = "whole_frequency")
    private Integer wholeFrequency;

    /**
     * 全部用户时间
     */
    @TableField(value = "whole_time")
    private Integer wholeTime;

    /**
     * 全部用户时间单位
     */
    @TableField(value = "whole_time_unit")
    private String wholeTimeUnit;

    /**
     * 限制类型，0=限流，1=停用
     */
    @TableField(value = "limit_type")
    private String limitType;

    /**
     * 停用提示，当限制类型=1时，接口返回的提示内容
     */
    @TableField(value = "ret_msg")
    private String retMsg;

    /**
     * 限制状态，0=正常，1=停用
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
