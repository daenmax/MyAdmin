package cn.daenx.common.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通用修改状态
 */
@Data
public class ComStatusUpdVo {
    @NotBlank(message = "ID不能为空")
    private String id;
    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空")
    private String status;

}
