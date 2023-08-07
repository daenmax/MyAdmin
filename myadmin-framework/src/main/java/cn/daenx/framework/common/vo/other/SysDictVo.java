package cn.daenx.framework.common.vo.other;

import cn.daenx.framework.common.vo.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典表
 */
@Data
public class SysDictVo extends BaseEntity implements Serializable {
    private String id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
