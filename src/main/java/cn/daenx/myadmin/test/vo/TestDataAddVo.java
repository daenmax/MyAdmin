package cn.daenx.myadmin.test.vo.add;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestDataAddVo {
    @NotBlank(message = "内容不能为空")
    private String content;
}
