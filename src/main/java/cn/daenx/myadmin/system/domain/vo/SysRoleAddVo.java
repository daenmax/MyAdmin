package cn.daenx.myadmin.system.domain.vo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class SysRoleAddVo {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @NotNull(message = "菜单树选择项是否关联显示 不能为空")
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @NotNull(message = "部门树选择项是否关联显示 不能为空")
    private Boolean deptCheckStrictly;

    /**
     * 角色状态，0=正常，1=禁用
     */
    @NotNull(message = "角色状态不能为空")
    private String status;
    private String remark;
    private List<String> menuIds;

}
