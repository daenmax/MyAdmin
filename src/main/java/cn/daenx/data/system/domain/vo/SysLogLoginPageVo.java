package cn.daenx.data.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogLoginPageVo extends BasePageVo {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录结果，0=成功，1=失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
