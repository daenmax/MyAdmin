package cn.daenx.system.domain.vo;

import cn.daenx.framework.common.vo.TreeEntity;
import lombok.Data;

/**
 * 部门表
 */
@Data
public class SysDeptTree extends TreeEntity<SysDeptTree> {

    /**
     * 父级部门ID，顶级为0
     */
    private String parentId;

    /**
     * 部门名称
     */
    private String label;

    /**
     * 排序
     */
    private Integer weight;
}
