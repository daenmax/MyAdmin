package cn.daenx.common.vo.system.config;

import lombok.Data;

/**
 * 系统参数
 */
@Data
public class SysConfigVo{
    private String id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键值
     */
    private String keyVa;

    /**
     * 参数键值
     */
    private String value;

    /**
     * 系统内置，0=否，1=是
     */
    private String type;

    /**
     * 参数状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
