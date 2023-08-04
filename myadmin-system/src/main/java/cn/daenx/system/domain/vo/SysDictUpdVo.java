package cn.daenx.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysDictUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    @NotBlank(message = "字典名称不能为空")
    private String name;
    @NotBlank(message = "字典编码不能为空")
    private String code;
    private String status;
    private String remark;
}
