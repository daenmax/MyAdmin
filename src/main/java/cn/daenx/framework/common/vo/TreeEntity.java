package cn.daenx.framework.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Tree基类
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TreeEntity<T> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;


    /**
     * 父级部门ID，顶级为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 子部门
     */
    @TableField(exist = false)
    private List<T> children;

}
