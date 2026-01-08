package cn.daenx.modules.system.domain.dto.sysDict;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysDictAddDto {
    @NotBlank(message = "字典名称不能为空")
    private String name;
    @NotBlank(message = "字典编码不能为空")
    private String code;
    @NotBlank(message = "状态不能为空")
    private String status;
    private String remark;
}
