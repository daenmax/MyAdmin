package cn.daenx.system.domain.po;

import cn.daenx.framework.common.vo.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysMenu extends TreeEntity<SysMenu> {

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 排序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @TableField(value = "path")
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
    @TableField(value = "perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 显示状态，0=正常，1=隐藏
     */
    @TableField(value = "visible")
    private String visible;

    /**
     * 菜单状态，0=正常，1=禁用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 菜单类型，1=目录，2=菜单，3=按钮
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * 是否外链，0=是，1=否
     */
    @TableField(value = "is_frame")
    private String isFrame;

    /**
     * 是否缓存，0=缓存，1=不缓存
     */
    @TableField(value = "is_cache")
    private String isCache;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
