package cn.daenx.myadmin.test.vo.add;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestDataAddVo {
    @NotNull
    private String content;
}
