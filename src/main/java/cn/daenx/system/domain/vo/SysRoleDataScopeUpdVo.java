package cn.daenx.system.domain.vo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class SysRoleDataScopeUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限
     */
    @NotBlank(message = "数据权限不能为空")
    private String dataScope;

    /**
     * 部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @NotNull(message = "部门树选择项是否关联显示 不能为空")
    private Boolean deptCheckStrictly;

    private List<String> deptIds;


}
