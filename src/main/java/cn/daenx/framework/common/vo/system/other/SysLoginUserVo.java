package cn.daenx.framework.common.vo.system.other;

import cn.daenx.framework.common.constant.SystemConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 登录用户
 */
@Data
public class SysLoginUserVo implements Serializable {

    private String id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 部门ID
     */
    private String deptId;
    private String userType;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信open_id
     */
    private String openId;


    /**
     * 菜单权限
     */
    private Set<String> menuPermission;

    /**
     * 角色权限
     */
    private Set<String> rolePermission;

    /**
     * 角色列表
     */
    private List<SysRoleVo> roles;

    /**
     * 岗位列表
     */
    private List<SysPositionVo> positions;

    /**
     * 是否是超级管理员
     */
    private Boolean isAdmin;


}
