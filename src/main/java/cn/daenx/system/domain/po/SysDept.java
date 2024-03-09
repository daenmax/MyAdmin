package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.system.domain.dto.LeaderUserDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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

    /**
     * 父级部门ID，顶级为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 部门编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 部门简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 部门状态，0=正常，1=停用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 部门负责人 关联用户ID
     */
    @TableField(value = "leader_user_id")
    private String leaderUserId;

    /**
     * 层级，顶级为0
     */
    @TableField(value = "dept_level")
    private Integer deptLevel;

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

    @TableField(exist = false)
    private LeaderUserDto leaderUser;
}
