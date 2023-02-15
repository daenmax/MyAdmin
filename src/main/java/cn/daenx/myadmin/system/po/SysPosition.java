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
 * 岗位表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_position")
public class SysPosition extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 岗位名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 岗位编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 岗位简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 岗位状态，0=正常，1=禁用
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}