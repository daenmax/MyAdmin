package cn.daenx.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统注册默认信息
 */
@Data
@AllArgsConstructor
public class SysRegisterDefaultInfoVo implements Serializable {

    /**
     * 用户类型，必填
     */
    private String userType;

    /**
     * 部门编号，必填
     */
    private String[] deptCodes;

    /**
     * 岗位编码，非必填
     */
    private String[] positionCodes;

    /**
     * 角色编码，必填
     */
    private String[] roleCodes;


}
