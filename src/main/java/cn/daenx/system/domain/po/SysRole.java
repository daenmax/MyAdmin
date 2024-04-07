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

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysRole extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ExcelProperty(value = "角色ID")
    private String id;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    @ExcelProperty(value = "角色名称")
    private String name;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    @ExcelProperty(value = "角色编码")
    private String code;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ExcelProperty(value = "排序")
    private Integer sort;

    /**
     * 数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限
     */
    @TableField(value = "data_scope")
    @ExcelProperty(value = "数据权限", converter = ExcelConverter.class)
    @Dict(dictCode = "data_scope", custom = {})
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @TableField(value = "menu_check_strictly")
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @TableField(value = "dept_check_strictly")
    private Boolean deptCheckStrictly;

    /**
     * 角色状态，0=正常，1=禁用
     */
    @TableField(value = "status")
    @ExcelProperty(value = "角色状态", converter = ExcelConverter.class)
    @Dict(dictCode = "sys_normal_disable", custom = {})
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 用户是否有此角色
     */
    @TableField(exist = false)
    private boolean flag = false;
}
