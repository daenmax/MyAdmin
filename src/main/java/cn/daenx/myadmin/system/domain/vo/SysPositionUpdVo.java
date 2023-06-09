package cn.daenx.myadmin.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysPositionUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    private String name;
    /**
     * 岗位编码
     */
    @NotBlank(message = "岗位编码不能为空")
    private String code;

    /**
     * 岗位简介
     */
    private String summary;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;


    /**
     * 岗位状态，0=正常，1=禁用
     */
    @NotBlank(message = "岗位状态不能为空")
    private String status;

    /**
     * 备注
     */
    private String remark;
}
