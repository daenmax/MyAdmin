package cn.daenx.modules.system.domain.vo.sysDept;

import cn.daenx.framework.common.domain.vo.TreeEntity;
import lombok.Data;

/**
 * 部门表
 */
@Data
public class SysDeptTreeVo extends TreeEntity<SysDeptTreeVo> {

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
