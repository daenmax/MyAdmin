package cn.daenx.framework.common.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通用实体类
 */
@Data
public class ComIdVo {
    @NotBlank(message = "ID不能为空")
    private String id;

}
