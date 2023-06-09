package cn.daenx.myadmin.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDeptAddVo {
    /**
     * 父级部门ID，顶级为0
     */
    @NotBlank(message = "父级ID不能为空")
    private String parentId;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 部门编号
     */
    @NotBlank(message = "部门编号不能为空")
    private String code;

    /**
     * 部门简介
     */
    private String summary;

    /**
     * 部门负责人 关联用户ID
     */
    private String leaderUserId;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;


    /**
     * 部门状态，0=正常，1=禁用
     */
    @NotBlank(message = "部门状态不能为空")
    private String status;

    /**
     * 备注
     */
    private String remark;
}
