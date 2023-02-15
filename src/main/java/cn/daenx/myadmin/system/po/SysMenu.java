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
 * 菜单表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class SysMenu extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 父级部门ID，顶级为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 路由地址
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 路由参数
     */
    @TableField(value = "query_param")
    private String queryParam;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 权限标识
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 显示状态，0=正常，1=隐藏
     */
    @TableField(value = "visible")
    private Integer visible;

    /**
     * 菜单状态，0=正常，1=禁用
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 菜单类型，1=目录，2=菜单，3=按钮
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 是否外链，0=是，1=否
     */
    @TableField(value = "is_frame")
    private Integer isFrame;

    /**
     * 是否缓存，0=缓存，1=不缓存
     */
    @TableField(value = "is_cache")
    private Integer isCache;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}