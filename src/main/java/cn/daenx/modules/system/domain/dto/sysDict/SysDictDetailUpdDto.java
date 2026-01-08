package cn.daenx.modules.system.domain.dto.sysDict;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDictDetailUpdDto {
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String label;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    private String value;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
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
    @NotBlank(message = "字典状态不能为空")
    private String status;

    /**
     * 备注
     */
    private String remark;
}
