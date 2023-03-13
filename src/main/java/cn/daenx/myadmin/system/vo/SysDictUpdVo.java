package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysDictUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    private String name;
    private String code;
    private String status;
    private String remark;
}
