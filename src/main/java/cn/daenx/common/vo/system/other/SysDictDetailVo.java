package cn.daenx.common.vo.system.other;

import cn.daenx.common.vo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 字典明细表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictDetailVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 字典状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
