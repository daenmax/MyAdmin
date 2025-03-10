package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.serializer.annotation.Dict;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysPosition extends BaseEntity implements Serializable {

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位名称")
    @TableField(value = "name")
    private String name;

    /**
     * 岗位编码
     */
    @ExcelProperty(value = "岗位编码")
    @TableField(value = "code")
    private String code;

    /**
     * 岗位简介
     */
    @ExcelProperty(value = "岗位简介")
    @TableField(value = "summary")
    private String summary;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 岗位状态，0=正常，1=禁用
     */
    @ExcelProperty(value = "岗位状态", converter = ExcelConverter.class)
//    @Dict(custom = {@DictDetail(value = "0", label = "正常"), @DictDetail(value = "1", label = "禁用")})
    @Dict(dictCode = "sys_normal_disable", custom = {})
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;
}
