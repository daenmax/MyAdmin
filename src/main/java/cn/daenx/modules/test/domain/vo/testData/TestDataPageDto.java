package cn.daenx.modules.test.domain.vo.testData;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataPageDto extends BasePageDto {
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
