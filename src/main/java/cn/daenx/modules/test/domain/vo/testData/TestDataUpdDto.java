package cn.daenx.modules.test.domain.vo.testData;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDataUpdDto {
    @NotBlank(message = "ID不能为空")
    private String id;
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
