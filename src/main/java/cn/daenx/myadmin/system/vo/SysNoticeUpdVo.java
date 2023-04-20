package cn.daenx.myadmin.system.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysNoticeUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
