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
 * 部门表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dept")
public class SysDept extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 父级部门ID，顶级为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 所有上级列表，顶级填0
     */
    @TableField(value = "all_parent_id")
    private String allParentId;

    /**
     * 部门名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 部门简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 部门状态，0=正常，1=停用
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 部门负责人 关联用户ID
     */
    @TableField(value = "leader_user_id")
    private String leaderUserId;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}