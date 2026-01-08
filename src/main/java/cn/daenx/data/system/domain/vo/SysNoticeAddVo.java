package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysNoticeAddVo {
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotBlank(message = "类型不能为空")
    private String type;
    @NotBlank(message = "状态不能为空")
    private String status;
    private String remark;
}
