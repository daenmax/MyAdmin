package cn.daenx.test.domain.po;

import cn.daenx.framework.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 测试树表数据
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "test_data_tree")
public class TestDataTree extends BaseEntity implements Serializable {

    /**
     * 父级ID，顶级为0
     */
    @TableField(value = "parent_id")
    private String parentId;

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    /**
     * 类型，0=民生，1=科技
     */
    @TableField(value = "type")
    private String type;

    /**
     * 状态，0=正常，1=停用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
