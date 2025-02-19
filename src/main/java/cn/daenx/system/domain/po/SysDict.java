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
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dict")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysDict extends BaseEntity implements Serializable {

    /**
     * 字典名称
     */
    @ExcelProperty(value = "字典名称")
    @TableField(value = "name")
    private String name;

    /**
     * 字典编码
     */
    @ExcelProperty(value = "字典编码")
    @TableField(value = "code")
    private String code;

    /**
     * 字典状态，0=正常，1=禁用
     */
    @ExcelProperty(value = "字典状态", converter = ExcelConverter.class)
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
