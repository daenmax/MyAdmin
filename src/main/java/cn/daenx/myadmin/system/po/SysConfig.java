package cn.daenx.myadmin.system.po;

import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_config")
public class SysConfig extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 参数名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 参数键值
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * 参数键值
     */
    @TableField(value = "`value`")
    private String value;

    /**
     * 系统内置，0=否，1=是
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}