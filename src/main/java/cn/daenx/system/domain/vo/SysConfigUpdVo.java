package cn.daenx.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysConfigUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 参数名称
     */
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    private String keyVa;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    private String value;

    /**
     * 系统内置，0=否，1=是
     */
    @NotBlank(message = "系统内置不能为空")
    private String type;

    /**
     * 参数状态，0=正常，1=禁用
     */
    @NotBlank(message = "参数状态不能为空")
    private String status;

    /**
     * 备注
     */
    private String remark;
}
