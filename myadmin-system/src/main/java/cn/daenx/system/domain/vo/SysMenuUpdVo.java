package cn.daenx.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysMenuUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

    private String parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 排序
     */
    @NotNull(message = "显示排序不能为空")
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由参数
     */
    private String queryParam;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 显示状态，0=正常，1=隐藏
     */
    private String visible;

    /**
     * 菜单状态，0=正常，1=禁用
     */
    private String status;

    /**
     * 菜单类型，1=目录，2=菜单，3=按钮
     */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /**
     * 是否外链，0=是，1=否
     */
    private String isFrame;

    /**
     * 是否缓存，0=缓存，1=不缓存
     */
    private String isCache;

    /**
     * 备注
     */
    private String remark;

}
