package cn.daenx.myadmin.system.vo.system;

import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.po.SysRole;
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
    private List<SysRole> roles;

    /**
     * 岗位列表
     */
    private List<SysPosition> positions;

    public boolean isAdmin() {
        return SystemConstant.IS_ADMIN_ID.equals(this.id);
    }


}
