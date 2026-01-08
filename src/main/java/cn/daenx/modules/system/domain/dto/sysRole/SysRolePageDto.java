package cn.daenx.modules.system.domain.dto.sysRole;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysRolePageDto extends BasePageDto {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限
     */
    private String dataScope;

    /**
     * 角色状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}
