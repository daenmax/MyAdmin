package cn.daenx.myadmin.test.dto;

import cn.daenx.myadmin.common.annotation.Dict;
import cn.daenx.myadmin.common.annotation.DictDetail;
import cn.daenx.myadmin.common.excel.DictConverter;
import cn.daenx.myadmin.common.vo.BaseDto;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
//导出时忽略没有@ExcelProperty的字段
@ExcelIgnoreUnannotated
public class TestDataPageDto extends BaseDto {
    @ExcelProperty(value = "id")
    private String id;
    @ExcelProperty(value = "标题")
    private String title;
    @ExcelProperty(value = "内容")
    private String content;
    @ExcelProperty(value = "类型", converter = DictConverter.class)
    //使用自定义字典进行翻译，意思是直接写死在代码里的
    @Dict(custom = {@DictDetail(value = "0", label = "民生"), @DictDetail(value = "1", label = "科技"), @DictDetail(value = "2", label = "农业"), @DictDetail(value = "3", label = "其他")})
//    @Dict(dictCode = "test_data_type", custom = {})
    private String type;
    @ExcelProperty(value = "备注")
    private String remark;
}
