package cn.daenx.myadmin.test.dto;

import cn.daenx.myadmin.common.vo.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestDataPageDto extends BaseDto {
    private String id;
    private String title;
    private String content;
    private String remark;
}
