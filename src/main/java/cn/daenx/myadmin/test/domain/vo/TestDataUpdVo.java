package cn.daenx.myadmin.test.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDataUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
