package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.framework.excel.ExcelConverter;
import cn.daenx.framework.dictMasked.annotation.Dict;
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
    * 字典明细表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dict_detail")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysDictDetail extends BaseEntity implements Serializable {

    /**
     * 字典编码
     */
    @ExcelProperty(value = "字典编码")
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典标签
     */
    @ExcelProperty(value = "字典标签")
    @TableField(value = "label")
    private String label;

    /**
     * 字典键值
     */
    @ExcelProperty(value = "字典键值")
    @TableField(value = "value")
    private String value;

    /**
     * 排序
     */
    @ExcelProperty(value = "字典排序")
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 样式属性（其他样式扩展）
     */
    @ExcelProperty(value = "样式属性")
    @TableField(value = "css_class")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @ExcelProperty(value = "回显样式")
    @TableField(value = "list_class")
    private String listClass;

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
