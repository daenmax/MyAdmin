package cn.daenx.test.domain.po;


import cn.daenx.framework.common.vo.BaseEntity;
import cn.daenx.framework.serializer.annotation.Dict;
import cn.daenx.framework.serializer.annotation.DictDetail;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    //字段脱敏
//    @Masked(type = MaskedType.NAME)
    private String content;

    /**
     * 类型，0=民生，1=科技
     */
    @TableField(value = "type")
    //使用自定义字典进行翻译，意思是直接写死在代码里的
    @Dict(custom = {@DictDetail(value = "0", label = "民生"), @DictDetail(value = "1", label = "科技"), @DictDetail(value = "2", label = "农业"), @DictDetail(value = "3", label = "其他")})
//    @Dict(dictCode = "test_data_type", custom = {})
    private String type;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态，0=正常，1=禁用
     */
    @TableField(value = "status")
//    @Dict(custom = {@DictDetail(value = "0", label = "正常"), @DictDetail(value = "1", label = "禁用")})
    //使用系统字典表里的翻译数据，推荐
    @Dict(dictCode = "sys_normal_disable", custom = {})
    private String status;
}
