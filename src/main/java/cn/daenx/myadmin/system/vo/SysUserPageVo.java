package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SysUserPageVo extends BasePageVo {

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 账号状态，0=正常，1=停用，2=注销
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
