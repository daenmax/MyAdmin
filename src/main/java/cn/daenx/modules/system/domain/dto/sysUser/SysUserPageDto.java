package cn.daenx.modules.system.domain.dto.sysUser;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserPageDto extends BasePageDto {

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 账号状态，0=正常，1=停用
     */
    private String status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户类型，具体看字典
     */
    private String userType;

    private String nickName;

    private String realName;

    private String age;

    private String sex;

    private String profile;

    private String userSign;
}
