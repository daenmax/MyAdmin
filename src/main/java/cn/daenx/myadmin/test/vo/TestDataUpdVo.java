package cn.daenx.myadmin.test.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDataUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    private String title;
    private String content;
    private String remark;
}
