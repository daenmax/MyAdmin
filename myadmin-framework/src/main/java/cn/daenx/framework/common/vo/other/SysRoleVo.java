package cn.daenx.framework.common.vo.other;

import cn.daenx.framework.common.vo.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
public class SysRoleVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限
     */
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean deptCheckStrictly;

    /**
     * 角色状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户是否有此角色
     */
    private boolean flag = false;
}
