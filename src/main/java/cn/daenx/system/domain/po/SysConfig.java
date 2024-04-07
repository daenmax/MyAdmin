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
 * 系统参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_config")
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class SysConfig extends BaseEntity implements Serializable {

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    @TableField(value = "name")
    private String name;

    /**
     * 参数键值
     */
    @ExcelProperty(value = "参数键值")
    @TableField(value = "key_va")
    private String keyVa;

    /**
     * 参数键值
     */
    @ExcelProperty(value = "参数键值")
    @TableField(value = "value")
    private String value;

    /**
     * 系统内置，0=否，1=是
     */
    @ExcelProperty(value = "系统内置", converter = ExcelConverter.class)
//    @Dict(custom = {@DictDetail(value = "0", label = "否"), @DictDetail(value = "1", label = "是")})
    @Dict(dictCode = "sys_yes_no", custom = {})
    @TableField(value = "type")
    private String type;

    /**
     * 参数状态，0=正常，1=禁用
     */
    @ExcelProperty(value = "参数状态", converter = ExcelConverter.class)
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
