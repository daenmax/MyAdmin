package cn.daenx.myadmin.test.po;


import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试数据
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "test_data")
public class TestData extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

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
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态，0=正常，1=禁用
     */
    @TableField(value = "`status`")
    private String status;
}
