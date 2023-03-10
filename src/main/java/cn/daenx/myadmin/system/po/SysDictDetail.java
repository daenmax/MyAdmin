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
    * 字典明细表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dict_detail")
public class SysDictDetail extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典标签
     */
    @TableField(value = "`label`")
    private String label;

    /**
     * 字典键值
     */
    @TableField(value = "`value`")
    private String value;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 样式属性（其他样式扩展）
     */
    @TableField(value = "css_class")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @TableField(value = "list_class")
    private String listClass;

    /**
     * 字典状态，0=正常，1=禁用
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
